package org.quixalert.br.presentation.pages.adoptions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.AdoptionT
import org.quixalert.br.domain.model.Message
import org.quixalert.br.utils.formatMessageTime
import java.util.UUID

// Global mocked URLs for avatars – replace with real URLs when ready.
val MOCK_USER_IMAGE_URL = "https://firebasestorage.googleapis.com/v0/b/quixalert.appspot.com/o/imagens%2FScreenshot%202025-02-14%20at%2016.06.44.png?alt=media&token=338974aa-2e2f-4a0b-b508-c660534907a0"
val MOCK_ATTENDANT_IMAGE_URL = "https://firebasestorage.googleapis.com/v0/b/quixalert.appspot.com/o/imagens%2FScreenshot%202025-02-14%20at%2016.06.59.png?alt=media&token=8cff36b7-f824-4e27-aed8-6ed80974d685"
val MOCK_USER_ID = "1"

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    adoption: AdoptionT,
    onBackClick: () -> Unit
) {
    val chatViewModel: ChatViewModel = hiltViewModel()
    val uiState by chatViewModel.uiState.collectAsState()

    LaunchedEffect(adoption.id) {
        chatViewModel.loadMessages(adoption.id)
    }

    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    fun addNewMessage(text: String) {
        val newMessage = Message(
            id = UUID.randomUUID().toString(),
            description = text,
            timestamp = System.currentTimeMillis(),
            userId = MOCK_USER_ID,
            adoptionId = adoption.id,
            isFromAttendant = false
        )
        chatViewModel.addMessage(newMessage)
        coroutineScope.launch {
            listState.animateScrollToItem(uiState.messages.size)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
            }
            Text(
                text = "Adoção do(a) ${adoption.animal?.name ?: "Animal"}",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if (uiState.messages.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Nenhuma mensagem", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.messages, key = { it.id }) { message ->
                        val isUserMessage = !message.userId.equals("attendant")
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + slideInVertically(initialOffsetY = { it * 2 })
                        ) {
                            ChatMessage(message = message, isUserMessage = isUserMessage)
                        }
                    }
                }
            }
        }
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Digite sua mensagem...") },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.LightGray.copy(alpha = 0.1f)
                )
            )
            Button(
                onClick = {
                    if (messageText.isNotBlank()) {
                        addNewMessage(messageText)
                        messageText = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF269996)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Enviar", style = MaterialTheme.typography.bodyMedium.copy(color = Color.White))
            }
        }
    }
}

@Composable
private fun ChatMessage(message: Message, isUserMessage: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isUserMessage) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        if (!isUserMessage) {
            AsyncImage(
                model = MOCK_ATTENDANT_IMAGE_URL,
                contentDescription = "Atendente",
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .shadow(10.dp) // Added shadow to match user image
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
        Column(
            modifier = Modifier
                .background(
                    color = if (isUserMessage) Color(0xFFE3F2FD) else Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            Text(text = message.description, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = message.timestamp.formatMessageTime(),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.End)
            )
        }
        if (isUserMessage) {
            Spacer(modifier = Modifier.size(4.dp))
            AsyncImage(
                model = MOCK_USER_IMAGE_URL,
                contentDescription = "Usuário",
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .shadow(10.dp)
            )
        }
    }
}