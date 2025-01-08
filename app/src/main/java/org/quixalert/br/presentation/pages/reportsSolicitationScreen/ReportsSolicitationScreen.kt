package org.quixalert.br.presentation.pages.reportsSolicitationScreen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import org.quixalert.br.R
import org.quixalert.br.domain.model.ReportType
import org.quixalert.br.presentation.pages.profile.IconTint

@Composable
fun ReportsSolicitationScreen(
    onBackClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onFormClick: () -> Unit = {}
) {
    val viewModel: ReportsSolicitationViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    var showSuccessDialog by remember { mutableStateOf(false) }

    if (uiState.submissionSuccess && !showSuccessDialog) {
        showSuccessDialog = true
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        TopBar(onBackClick = onBackClick, onMenuClick = onMenuClick)
        Text(
            text = "Fomulário de denúncia",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(top = 8.dp, start = 16.dp),
            color = MaterialTheme.colorScheme.onBackground,
        )

        ReportsSolicitationForm(viewModel)

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                title = { Text("Denúncia enviada com sucesso!") },
                text = { Text("Sua denúncia foi enviada com sucesso. Clique em OK para continuar.") },
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
}

@Composable
fun ReportsSolicitationForm(viewModel: ReportsSolicitationViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> imageUri = uri }
    )

    val textFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color(0xFFEEEEEE),
        unfocusedContainerColor = Color(0xFFEEEEEE),
        disabledContainerColor = Color(0xFFEEEEEE),
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(bottom = 72.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("Ambiental", "Maus Tratos", "Outros").forEach { label ->
                Button(
                    onClick = { viewModel.updateReportType(ReportType.valueOf(label.uppercase())) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.selectedType.name == label.uppercase()) Color(0xFF269996) else Color(0xFFB7B7B8)
                    ),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .height(26.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = label,
                        fontSize = 14.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }

        ImageSelection(
            imageUri = imageUri,
            imagePickerLauncher = imagePickerLauncher
        )

        Text(
            text = "Descrição",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
        OutlinedTextField(
            value = uiState.description,
            onValueChange = { viewModel.updateDescription(it) },
            placeholder = { Text("Ex: Solicitação de alvará para solicitação de construção de um prédio na rua ...") },
            colors = textFieldColors,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
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
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
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
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
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
            placeholder = { Text("Ex: Estou solicitando para..") },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            colors = textFieldColors
        )

        Button(
            onClick = { viewModel.submitReport() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF269996)
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Enviar Denúncia",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ImageSelection(
    imageUri: Uri?,
    imagePickerLauncher: ManagedActivityResultLauncher<String, Uri?>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable {
                imagePickerLauncher.launch("image/*")
            }
    ) {
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Imagem selecionada",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.default_local_item),
                contentDescription = "Imagem padrão",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
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