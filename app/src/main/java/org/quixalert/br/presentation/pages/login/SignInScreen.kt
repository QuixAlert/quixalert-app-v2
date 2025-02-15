package org.quixalert.br.presentation.pages.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import org.quixalert.br.presentation.components.StyledTextField
import org.quixalert.br.presentation.ui.theme.primaryBlue
import org.quixalert.br.presentation.ui.theme.primaryGreen

@Composable
fun SignInScreen(
    loginViewModel: LoginViewModel = viewModel(), // Obtém a instância do ViewModel
    onLoginSuccess: (String) -> Unit // Ação a ser realizada após o login bem-sucedido
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Collect the UI state as Compose state.
    val loginState by loginViewModel.loginUiState.collectAsState()

    LaunchedEffect(loginState.isSuccess) {
        if (loginState.isSuccess) {
            onLoginSuccess(loginState.userRegistrationData?.id ?: "")
        }
    }

    // Use LaunchedEffect para disparar efeitos colaterais apenas uma vez
    LaunchedEffect(loginState.isSuccess) {
        if (loginState.isSuccess) {
            onLoginSuccess(loginState.userRegistrationData?.id ?: "")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryGreen)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 53.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(83.dp))

            Text(
                text = "Olá!",
                fontSize = 64.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.333333).sp,
                color = primaryBlue
            )

            Text(
                text = "Bem-vindo de volta!",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.333333).sp,
                color = primaryBlue,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(107.dp))

            // Email field
            StyledTextField(
                value = email,
                onValueChange = { email = it },
                label = "Usuário/E-mail:",
                placeholder = "usuario@exemplo.com",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(35.dp))

            // Password field
            StyledTextField(
                value = password,
                onValueChange = { password = it },
                label = "Senha:",
                placeholder = "••••••••",
                isPassword = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Login Button
            Button(
                onClick = {
                    // Chama o método de login com as credenciais do usuário
                    loginViewModel.loginUser(email, password)
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(53.dp),
                colors = ButtonDefaults.buttonColors(primaryBlue),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(40.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Text(
                    text = "Entrar",
                    fontSize = 24.sp,
                    letterSpacing = (-0.333333).sp,
                    color = Color.White
                )
            }

            // Loading indicator (replace with your preferred implementation)
            if (loginState.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Carregando...",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Error message display
            if (!loginState.isSuccess && loginState.errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(
                    onClick = { /* ação para tratar erro, ex: limpar a mensagem ou tentar novamente */ }
                ) {
                    Text(
                        text = loginState.errorMessage ?: "Erro desconhecido",
                        color = Color.Red
                    )
                }
            }
        }
    }
}