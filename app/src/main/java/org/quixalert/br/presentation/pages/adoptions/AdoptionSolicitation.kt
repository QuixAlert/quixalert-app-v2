package org.quixalert.br.presentation.pages.adoptions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.Adoption
import org.quixalert.br.domain.model.Animal
import org.quixalert.br.utils.formatMessageTime
import java.util.UUID

// Dados mockados para teste
object MockData {
    var currentUser = User(
        id = "1",
        name = "João Silva",
        photoUrl = "https://example.com/joao.jpg"
    )

    val otherUser = User(
        id = "2",
        name = "Maria Oliveira",
        photoUrl = "https://example.com/maria.jpg"
    )

    val mockMessages = listOf(
        Message(
            id = "1",
            description = "Olá, gostaria de adotar o Rex!",
            timestamp = System.currentTimeMillis() - (3600 * 1000), // 1 hora atrás
            userId = "1"
        ),
        Message(
            id = "2",
            description = "Oi! Claro, vamos iniciar o processo de adoção.",
            timestamp = System.currentTimeMillis() - (3600 * 1000), // 1 hora atrás
            userId = "2"
        ),
        Message(
            id = "3",
            description = "Preciso dos seus documentos para avançarmos.",
            timestamp = System.currentTimeMillis() - (3600 * 1000), // 1 hora atrás
            userId = "2"
        ),
        Message(
            id = "4",
            description = "Acabei de enviar todos os documentos solicitados por email.",
            timestamp = System.currentTimeMillis() - (3600 * 1000), // 1 hora atrás
            userId = "1"
        ),
        Message(
            id = "5",
            description = "Perfeito! Vou analisar e retorno em breve.",
            timestamp = System.currentTimeMillis() - (3600 * 1000), // 1 hora atrás
            userId = "2"
        ),
        Message(
            id = "6",
            description = "Perfeito! Vou analisar e retorno em breve.",
            timestamp = System.currentTimeMillis() - (3600 * 1000), // 1 hora atrás
            userId = "2"
        ),
        Message(
            id = "7",
            description = "Perfeito! Vou analisar e retorno em breve.",
            timestamp = System.currentTimeMillis() - (3600 * 1000), // 1 hora atrás
            userId = "2"
        )
    )
}

// Exemplo de uso
@Composable
fun AdoptionSolicitationScreen(adoption: Adoption) {
    AdoptionRequestScreen(
        adoption = adoption,
        currentUser = MockData.currentUser,
        otherUser = MockData.otherUser,
        initialMessages = MockData.mockMessages,
        onBackClick = { },
        onSendClick = { },
        onEditClick = { }
    )
}

data class User(
    val id: String,
    val name: String,
    val photoUrl: String
)

data class Message(
    val id: String,
    val description: String,
    val timestamp: Long, // Timestamp do Firebase
    val userId: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdoptionRequestScreen(
    adoption: Adoption,
    currentUser: User,
    otherUser: User,
    initialMessages: List<Message>,
    onBackClick: () -> Unit,
    onSendClick: (String) -> Unit,
    onEditClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var messageText by remember { mutableStateOf("") }
        var messages by remember { mutableStateOf(initialMessages) }
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        // Função para adicionar nova mensagem
        fun addNewMessage(text: String) {
            val newMessage = Message(
                id = UUID.randomUUID().toString(),
                description = text,
                timestamp = System.currentTimeMillis(),
                userId = currentUser.id
            )
            messages = messages + newMessage

            // Rola para a última mensagem
            coroutineScope.launch {
                listState.animateScrollToItem(messages.size - 1)
            }

            // Chama o callback original
            onSendClick(text)
        }
        // Animal image with back button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            AsyncImage(
                model = adoption.petImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Back button with semi-transparent background
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(16.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.5f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar"
                )
            }
        }

        // Content with rounded top corners
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-20).dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = "Solicitação de adoção",
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            // Date information row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoColumn(
                    title = "Data da Solicitação",
                    value = "20/02/2024"
                )
                InfoColumn(
                    title = "Dias em aberto",
                    value = "20 dias"
                )
                InfoColumn(
                    title = "Prazo de Finalização",
                    value = "20/03/2024"
                )
            }

            // User info row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Solicitante",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AsyncImage(
                            model = otherUser.photoUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Text(text = otherUser.name)
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Status",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Em atendimento",
                        modifier = Modifier
                            .background(
                                color = Color(0xFFFFB74D),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        color = Color.White
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                state = listState
            ) {
                items(
                    items = messages,
                    key = { it.id }
                ) { message ->
                    val isCurrentUser = message.userId == currentUser.id
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(
                            initialOffsetY = { it * 2 }
                        ),
                        modifier = Modifier.animateItemPlacement()
                    ) {
                        ChatMessage(
                            message = message,
                            isCurrentUser = isCurrentUser
                        )
                    }
                }
            }

            // Input para nova mensagem
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    placeholder = { Text("Digite sua mensagem...") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.LightGray.copy(alpha = 0.1f)
                    )
                )

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            if (messageText.isNotBlank()) {
                                addNewMessage(messageText)
                                messageText = ""
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Green
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Enviar")
                    }

                    Button(
                        onClick = onEditClick,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFFB74D)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Editar")
                    }
                }
            }
        }
    }
}

    @Composable
private fun ChatMessage(
    message: Message,
    isCurrentUser: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = if (isCurrentUser) Alignment.End else Alignment.Start
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = if (isCurrentUser) Color(0xFFE3F2FD) else Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            Text(
                text = message.description,
                style = MaterialTheme.typography.body2
            )
            Text(
                text = message.timestamp.formatMessageTime(),
                style = MaterialTheme.typography.caption,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
private fun InfoColumn(
    title: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.caption
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}