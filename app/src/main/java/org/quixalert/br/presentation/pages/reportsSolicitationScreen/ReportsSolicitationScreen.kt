package org.quixalert.br.presentation.pages.reportsSolicitationScreen

import android.annotation.SuppressLint
import android.net.Uri
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import org.quixalert.br.R
import org.quixalert.br.presentation.pages.profile.IconTint

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun ReportsSolicitationScreen(onBackClick: () -> Unit = {}, onMenuClick: () -> Unit = {}) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        TopBar(
            onBackClick = onBackClick,
            onMenuClick = onMenuClick
        )
        Text(
            text = "Fomulário de denúncia",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(top = 8.dp, start = 16.dp)
        )


        ReportsSolicitationForm()
    }
}

@Composable
fun ReportsSolicitationForm() {
    var selectedTypeDocument by remember { mutableStateOf("Alvará") }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var motivation by remember { mutableStateOf(TextFieldValue("")) }
    var details by remember { mutableStateOf(TextFieldValue("")) }
    var address by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Lançador para selecionar uma imagem da galeria
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
                    onClick = { selectedTypeDocument = label },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTypeDocument == label) Color(0xFF269996) else Color(0xFFB7B7B8)
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
            // Se houver uma imagem selecionada, exibe
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
                    painter = painterResource(id = R.drawable.default_local_item), // Substitua pelo nome do seu recurso
                    contentDescription = "Imagem padrão",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Text(
            text = "Descrição",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = { Text("Ex: Solicitação de alvará para solicitação de construção de um prédio na rua ...") },
            colors = textFieldColors,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Text(
            text = "Endereço",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        TextField(
            value = address,
            onValueChange = { address = it },
            placeholder = { Text("Ex: Avenida Santos Marcos, 1219") },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            colors = textFieldColors
        )

        Text(
            text = "Motivo da solicitação",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        TextField(
            value = motivation,
            onValueChange = { motivation = it },
            placeholder = { Text("Ex: Para realizar uma construção...") },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            colors = textFieldColors
        )

        Text(
            text = "Detalhes Extras (opcional)",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        TextField(
            value = details,
            onValueChange = { details = it },
            placeholder = { Text("Ex: Estou solicitando para..") },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            colors = textFieldColors
        )

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                Color(0xFF269996)
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