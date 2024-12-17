package org.quixalert.br.view.pages.donation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DonationScreen(onBackClick: () -> Unit) {
    var donationAmount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top Image Section with Yellow Background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFFF8BC35))
        ) {
            // Back Button
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.White
                )
            }

            // Donation Text
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp, end = 16.dp, top = 60.dp),
            ) {
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                value = donationAmount,
                onValueChange = { donationAmount = it },
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
                    .aspectRatio(1f)
                    .background(
                        Color.LightGray,
                        RoundedCornerShape(8.dp)
                    )
            )

            Button(
                onClick = { /* Handle form submission */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp), // Defina uma altura fixa para o botão
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
    }
}