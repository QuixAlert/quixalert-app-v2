package org.quixalert.br.presentation.pages.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.quixalert.br.domain.model.UserRegistrationData
import org.quixalert.br.presentation.components.StyledTextField
import org.quixalert.br.presentation.components.WaveBackground
import org.quixalert.br.presentation.pages.register.RegisterViewModel
import org.quixalert.br.presentation.ui.theme.poppinsFamily
import org.quixalert.br.presentation.ui.theme.primaryBlue
import org.quixalert.br.presentation.ui.theme.primaryGreen

@Preview
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
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
                .padding(horizontal = 54.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            
            Text(
                text = "Olá!",
                fontSize = 36.sp,
                color = primaryBlue,
                fontFamily = poppinsFamily(),
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Seja bem-vindo ao QuixAlert!",
                fontSize = 20.sp,
                color = primaryBlue,
                fontFamily = poppinsFamily(),
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Name field
            StyledTextField(
                value = name,
                onValueChange = { name = it },
                label = "Nome:",
                placeholder = "Nome Completo",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email field
            StyledTextField(
                value = email,
                onValueChange = { email = it },
                label = "E-mail:",
                placeholder = "usuario@exemplo.com",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Phone field
            StyledTextField(
                value = phone,
                onValueChange = { phone = it },
                label = "Celular:",
                placeholder = "(99) 9 9999-9999",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Birth date field
            StyledTextField(
                value = birthDate,
                onValueChange = { birthDate = it },
                label = "Data de nascimento:",
                placeholder = "DD/MM/AAAA",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (validateInputs(name, email, phone, birthDate)){
                        val userRegistrationData = UserRegistrationData(
                            name = name,
                            email = email,
                            phone = phone,
                            birthDate = birthDate
                        )
                        onNextStep(userRegistrationData)
                    }else {

                    }

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


private fun validateInputs(name: String, email: String, phone: String, birthDate: String): Boolean {
    return name.isNotBlank() && email.isNotBlank() && phone.isNotBlank() && birthDate.isNotBlank()
}