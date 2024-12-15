package org.quixalert.br.view.pages.login

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import org.quixalert.br.R
import org.quixalert.br.model.UserRegistrationData
import org.quixalert.br.view.ui.theme.primaryBlue
import org.quixalert.br.view.ui.theme.primaryGreen

@Composable
fun RegisterStepTwoScreen(
    initialData: UserRegistrationData,
    onRegisterComplete: (UserRegistrationData) -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    
    val context = LocalContext.current
    
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
                .padding(horizontal = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Profile Image Selection
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White)
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
                        modifier = Modifier.size(40.dp),
                        tint = primaryBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Adicionar foto de perfil",
                color = primaryBlue,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Username field
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nome de usuário") },
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

            // Password field
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
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

            // Confirm Password field
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar senha") },
                visualTransformation = PasswordVisualTransformation(),
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
                    .fillMaxWidth()
                    .height(59.dp),
                colors = ButtonDefaults.buttonColors(primaryBlue),
                shape = RoundedCornerShape(40.dp)
            ) {
                Text(
                    text = "Cadastrar",
                    fontSize = 24.sp,
                    color = Color.White
                )
            }
        }
    }
} 