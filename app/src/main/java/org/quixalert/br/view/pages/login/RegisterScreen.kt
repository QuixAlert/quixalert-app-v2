package org.quixalert.br.view.pages.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.quixalert.br.R
import org.quixalert.br.model.UserRegistrationData
import org.quixalert.br.view.ui.theme.primaryBlue
import org.quixalert.br.view.ui.theme.primaryGreen

@Composable
fun RegisterScreen(
    onNextStep: (UserRegistrationData) -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryGreen)
    ) {
        // Reuse wave background
        WaveBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            
            Text(
                text = "Olá!",
                fontSize = 36.sp,
                color = primaryBlue
            )
            
            Text(
                text = "Seja bem-vindo ao QuixAlert!",
                fontSize = 20.sp,
                color = primaryBlue
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Name field
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome") },
                placeholder = { Text("Nome Completo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = primaryBlue,
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email field
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                placeholder = { Text("usuario@exemplo.com") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = primaryBlue,
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Phone field
            TextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Celular") },
                placeholder = { Text("(99) 9 9999-9999") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = primaryBlue,
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Birth date field
            TextField(
                value = birthDate,
                onValueChange = { birthDate = it },
                label = { Text("Data de nascimento") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = primaryBlue,
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { 
                    onNextStep(
                        UserRegistrationData(
                            name = name,
                            email = email,
                            phone = phone,
                            birthDate = birthDate
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(59.dp),
                colors = ButtonDefaults.buttonColors(primaryBlue),
                shape = RoundedCornerShape(40.dp)
            ) {
                Text(
                    text = "Próximo",
                    fontSize = 24.sp,
                    color = Color.White
                )
            }
        }
    }
} 