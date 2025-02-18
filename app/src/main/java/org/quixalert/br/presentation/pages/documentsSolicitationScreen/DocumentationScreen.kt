package org.quixalert.br.presentation.pages.documentsSolicitationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.quixalert.br.domain.model.Document
import org.quixalert.br.domain.model.DocumentStatus
import org.quixalert.br.domain.model.User

val MOCKED_DOCUMENTATION_PHOTO = "https://static.vecteezy.com/system/resources/thumbnails/047/201/337/small_2x/pile-of-documents-sat-on-desk-in-office-was-pile-of-documents-that-had-been-prepared-to-be-presented-at-meeting-as-summary-of-annual-operations-documents-prepared-for-storage-and-piled-on-the-desk-photo.jpg"

@Composable
fun DocumentationScreen(
    document: Document,
    onBackClick: () -> Unit,
    onOpenChatClick: () -> Unit,
    user: User
) {
    var showInfoDialog by remember { mutableStateOf(false) }
    val clipboardManager = LocalClipboardManager.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top image header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                AsyncImage(
                    model = MOCKED_DOCUMENTATION_PHOTO,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.White.copy(alpha = 0.5f), CircleShape)
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
            }
            // Main content section
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-20).dp)
                    .background(Color.White, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .padding(16.dp)
            ) {
                // Header Row with title and info button
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Solicitação de Documentação",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { showInfoDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info",
                            tint = Color.Gray
                        )
                    }
                }

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
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            AsyncImage(
                                model = user.profileImage,
                                contentDescription = "Imagem do solicitante",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                            )
                            Text(text = user.name, color = Color.Black)
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Status",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = when (document.status) {
                                DocumentStatus.EM_ANDAMENTO -> "Em atendimento"
                                DocumentStatus.CONCLUIDO-> "Aprovado"
                            },
                            modifier = Modifier
                                .background(Color(0xFF269996), RoundedCornerShape(16.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                // "Detalhes da Adoção" section with scrolling enabled only for this area.
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text(
                        text = "Detalhes da solicitação",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Wrap the details in a Box with a fixed height and vertical scroll
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Column {
                            DetailItem(label = "Tipo do documento", value = document.documentType.toString())
                            DetailItem(label = "Endereço", value = document.address)
                            DetailItem(label = "Razão para solicitação", value = document.reason)
                        }
                    }
                }
            }
        }
        // Floating Action Button to open chat
        //muda aqui
        FloatingActionButton(
            onClick = onOpenChatClick,
            containerColor = Color(0xFF269996),
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 12.dp, bottom = 95.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.MailOutline,
                contentDescription = "Abrir Chat",
                tint = Color.White
            )
        }
    }

    // Info Dialog showing the adoption request ID (with option to copy)
    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = {
                Text(
                    "ID da Solicitação",
                    style = MaterialTheme.typography.titleLarge.copy(color = Color.Black),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            },
            text = {
                Text(
                    document.id,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Black),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(document.id))
                        showInfoDialog = false
                    }
                ) {
                    Text(
                        "Copiar",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showInfoDialog = false }) {
                    Text(
                        "Fechar",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            },
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
private fun InfoColumn(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, style = MaterialTheme.typography.labelSmall)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
private fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
        Text(
            text = value.ifEmpty { "N/A" },
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.Black
        )
    }
}