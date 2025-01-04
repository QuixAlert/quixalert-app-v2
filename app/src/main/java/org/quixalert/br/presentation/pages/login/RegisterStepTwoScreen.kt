package org.quixalert.br.presentation.pages.login

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import org.quixalert.br.R
import org.quixalert.br.domain.model.UserRegistrationData
import org.quixalert.br.presentation.components.StyledTextField
import org.quixalert.br.presentation.components.WaveBackground
import org.quixalert.br.presentation.ui.theme.primaryBlue
import org.quixalert.br.presentation.ui.theme.primaryGreen

@Composable
fun RegisterStepTwoScreen(
    initialData: UserRegistrationData,
    onRegisterComplete: (UserRegistrationData) -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryGreen)
    ) {
        WaveBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 53.dp),  // Exact padding from Figma
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(83.dp))  // Matches Figma spacing
            
            Text(
                text = "Olá!",
                fontSize = 64.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.333333).sp,
                color = primaryBlue
            )
            
            Text(
                text = "Seja bem-vindo ao QuixAlert!",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.333333).sp,
                color = primaryBlue,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(37.dp))

            // Profile Image Selection
            Box(
                modifier = Modifier
                    .size(86.dp)  // Exact size from Figma
                    .shadow(
                        elevation = 4.dp,
                        shape = CircleShape,
                        spotColor = Color(0x40000000)
                    )
                    .background(Color(0xFFD9D9D9), CircleShape)  // Matches Figma color
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUri != null) {
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = "Selected profile image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_photo),
                        contentDescription = "Add photo",
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF50555C)
                    )
                }
            }

            Text(
                text = "Adicionar foto de perfil",
                color = primaryBlue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.333333).sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Username field
            StyledTextField(
                value = username,
                onValueChange = { username = it },
                label = "Usuário:",
                placeholder = "usuario.exemplo",
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

            Spacer(modifier = Modifier.height(35.dp))

            // Confirm Password field
            StyledTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirmar senha:",
                placeholder = "••••••••",
                isPassword = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (password == confirmPassword) {
                        onRegisterComplete(
                            initialData.copy(
                                username = username,
                                password = password,
                                profileImage = selectedImageUri
                            )
                        )
                    }
                },
                modifier = Modifier
                    .width(172.dp)  // Exact width from Figma
                    .height(53.dp),  // Exact height from Figma
                colors = ButtonDefaults.buttonColors(primaryBlue),
                shape = RoundedCornerShape(40.dp)
            ) {
                Text(
                    text = "Registrar",
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