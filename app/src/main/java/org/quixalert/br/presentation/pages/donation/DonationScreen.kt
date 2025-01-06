package org.quixalert.br.presentation.pages.donation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.quixalert.br.R
import org.quixalert.br.presentation.pages.profile.IconTint

@Composable
fun DonationScreen(
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit,
    onFormClick: () -> Unit,
    viewModel: DonationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var showSuccessDialog by remember { mutableStateOf(false) }

    if (uiState.submissionSuccess && !showSuccessDialog) {
        showSuccessDialog = true
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top Image Section with Yellow Background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8BC35))
        ) {
            // Donation Text
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp),
            ) {
                TopBar(
                    onBackClick = onBackClick,
                    onMenuClick = onMenuClick
                )
                Text(
                    text = "Não pode adotar agora?\nFaça uma doação!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Precisamos de ração e remédio!",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = Color.Black
                )
            }
        }

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 54.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Realizar Doação",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            // Donation Input
            OutlinedTextField(
                value = uiState.donationAmount,
                onValueChange = { viewModel.updateDonationAmount(it) },
                label = { Text("Valor") },
                prefix = { Text("R$ ") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.LightGray.copy(alpha = 0.2f),
                        RoundedCornerShape(8.dp)
                    ),
                shape = RoundedCornerShape(8.dp)
            )

            Text(
                text = "Se preferir você pode entregar presencialmente no endereço",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            // Map Placeholder - Square shape with width equal to screen width
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.LightGray,
                        RoundedCornerShape(8.dp)
                    ),
            ) {
                Image(
                    painter = painterResource(R.drawable.news1),
                    contentDescription = "Map",
                    contentScale = ContentScale.FillHeight
                )
            }

            Button(
                onClick = { viewModel.submitDonation() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF269996)
                )
            ) {
                Text(
                    text = "Realizar Doação!",
                    fontSize = 16.sp, // Tamanho da fonte visível
                    color = Color.White
                )
            }
        }

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                title = { Text("Doação realizada com sucesso!") },
                text = { Text("Obrigado pela sua contribuição!") },
                confirmButton = {
                    Button(onClick = {
                        viewModel.resetState()
                        onFormClick()
                        showSuccessDialog = false
                    }) {
                        Text("OK")
                    }
                }
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
