package org.quixalert.br

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.quixalert.br.domain.model.UserRegistrationData
import org.quixalert.br.presentation.components.FloatingMenu
import org.quixalert.br.presentation.components.HeaderSection
import org.quixalert.br.presentation.components.NavigationBarM3
import org.quixalert.br.presentation.pages.adoptions.AdoptionFormScreen
import org.quixalert.br.presentation.pages.adoptions.AdoptionScreen
import org.quixalert.br.presentation.pages.animal.AnimalDetailsScreen
import org.quixalert.br.presentation.pages.animal.mockPetDetail
import org.quixalert.br.presentation.ui.theme.AppTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Preview
fun App() {
    var currentScreen by remember { mutableStateOf("login") }
    var registrationData by remember { mutableStateOf<UserRegistrationData?>(null) }
    var isFloatingMenuVisible by remember { mutableStateOf(false) }

    AppTheme {
        var modifierTopBarBlur = if (isFloatingMenuVisible) {
            Modifier.background(Color.Black.copy(alpha = 0.5f))
        } else {
            Modifier.background(Color.Transparent)
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background),
            topBar = {
                    Column(
                        modifier = modifierTopBarBlur
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        HeaderSection()
                    }
            },

            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    AdoptionScreen()
//                    AdoptionFormScreen(pet = mockPetDetail, onBackClick = {})
//                    AnimalDetailsScreen(animalId = "ac039f10-f695-4cd3-b4cb-e7982442b539")

                    if (isFloatingMenuVisible) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f))
                                .blur(500.dp)
                        )
                    }
                }
            },

            bottomBar = {
                Column()
                {
                    if (isFloatingMenuVisible) {
                        FloatingMenu(
                            modifier = Modifier.padding(bottom = 18.dp)
                        )
                    }
                    NavigationBarM3(
                        onPlusClick = { isFloatingMenuVisible = !isFloatingMenuVisible },
                        onOtherCLick = {isFloatingMenuVisible = false}
                    )
                }
            }
        )
    }
}


//when (currentScreen) {
//    "login" -> LoginScreen(
//    onRegisterClick = { currentScreen = "register" },
//    onLoginClick = { currentScreen = "signin" }
//    )
//    "signin" -> SignInScreen()
//    "register" -> RegisterScreen(
//    onNextStep = { data ->
//        registrationData = data
//        currentScreen = "register_step_two"
//    }
//    )
//    "register_step_two" -> RegisterStepTwoScreen(
//    initialData = registrationData ?: UserRegistrationData(),
//    onRegisterComplete = { finalData ->
//        // Handle registration completion
//        currentScreen = "login"
//    }
//    )
//}
//}