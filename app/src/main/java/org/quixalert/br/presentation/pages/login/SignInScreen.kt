package org.quixalert.br.presentation.pages.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.quixalert.br.R
import org.quixalert.br.presentation.components.StyledTextField
import org.quixalert.br.presentation.components.WaveBackground
import org.quixalert.br.presentation.ui.theme.primaryBlue
import org.quixalert.br.presentation.ui.theme.primaryGreen

@Composable
fun SignInScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryGreen)
    ) {
        WaveBackground()

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

            // Username/Email field
            StyledTextField(
                value = email,
                onValueChange = { email = it },
                label = "Usuário/E-mail:",
                placeholder = "usuario@exemplo.com",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(35.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                StyledTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Senha:",
                    placeholder = "••••••••",
                    isPassword = true,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Text(
                    text = "Esqueci minha senha",
                    color = primaryBlue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = (-0.333333).sp,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 4.dp, end = 8.dp)
                        .clickable { /* Handle forgot password */ }
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .width(172.dp)
                    .height(53.dp),
                colors = ButtonDefaults.buttonColors(primaryBlue),
                shape = RoundedCornerShape(40.dp),
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

            Spacer(modifier = Modifier.height(27.dp))

            Text(
                text = "Ou conecte-se usando",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.333333).sp,
                color = primaryBlue
            )

            Spacer(modifier = Modifier.height(27.dp))

            // Social login icons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 56.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.facebook_logo),
                    contentDescription = "Facebook Login",
                    modifier = Modifier.size(35.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = "Google Login",
                    modifier = Modifier.size(35.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.microsoft_logo),
                    contentDescription = "Microsoft Login",
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    }
} 