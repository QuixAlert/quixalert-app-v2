package org.quixalert.br.presentation.pages.documentsSolicitationScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.quixalert.br.domain.model.DocumentType
import org.quixalert.br.presentation.pages.profile.IconTint

@Composable
fun DocumentsSolicitationScreen(
    onBackClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onFormClick: () -> Unit = {},
    viewModel: DocumentsSolicitationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var showSuccessDialog by remember { mutableStateOf(false) }

    if (uiState.submissionSuccess && !showSuccessDialog) {
        showSuccessDialog = true
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 32.dp)
    ) {
        TopBar(onBackClick = onBackClick, onMenuClick = onMenuClick)

        Text(
            text = "Fomulário de solicitação de documentos",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(top = 8.dp),
            color = MaterialTheme.colorScheme.onBackground
        )

        DocumentFormFields(viewModel)

        Button(
            onClick = { viewModel.submitDocument() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF269996)),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Solicitar Documento",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Documento solicitado com sucesso!") },
            text = { Text("Seu documento foi solicitado com sucesso. Clique em OK para continuar.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.resetSubmissionState()
                    onFormClick()
                    showSuccessDialog = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF269996)
                )) {
                    Text("OK")
                }

            }
        )
    }
}

@Composable
fun DocumentFormFields(viewModel: DocumentsSolicitationViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    val textFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color(0xFFEEEEEE),
        unfocusedContainerColor = Color(0xFFEEEEEE),
        disabledContainerColor = Color(0xFFEEEEEE),
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DocumentType.entries.forEach { type ->
            Button(
                onClick = { viewModel.updateDocumentType(type) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (uiState.selectedTypeDocument == type) Color(0xFF269996) else Color(0xFFB7B7B8)
                ),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.height(26.dp).weight(1f)
            ) {
                Text(
                    text = type.label,
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    Text(
        text = "Descrição",
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onBackground
    )
    OutlinedTextField(
        value = uiState.description,
        onValueChange = { viewModel.updateDescription(it) },
        placeholder = { Text("Ex: Solicitação de alvará para construção de um prédio...") },
        colors = textFieldColors,
        modifier = Modifier.fillMaxWidth().height(150.dp)
    )

    Text(
        text = "Endereço",
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onBackground
    )
    TextField(
        value = uiState.address,
        onValueChange = { viewModel.updateAddress(it) },
        placeholder = { Text("Ex: Avenida Santos Marcos, 1219") },
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)),
        colors = textFieldColors
    )

    Text(
        text = "Motivo da solicitação",
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onBackground
    )
    TextField(
        value = uiState.motivation,
        onValueChange = { viewModel.updateMotivation(it) },
        placeholder = { Text("Ex: Para realizar uma construção...") },
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)),
        colors = textFieldColors
    )

    Text(
        text = "Detalhes Extras (opcional)",
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onBackground
    )
    TextField(
        value = uiState.details,
        onValueChange = { viewModel.updateDetails(it) },
        placeholder = { Text("Ex: Estou solicitando para...") },
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)),
        colors = textFieldColors
    )
}

@Composable
fun TopBar(
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                tint = IconTint
            )
        }

        IconButton(onClick = onMenuClick) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = IconTint
            )
        }
    }
}