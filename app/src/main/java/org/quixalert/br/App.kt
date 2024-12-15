package org.quixalert.br

import AppTheme
import LocalStrings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.quixalert.br.view.pages.home.HomeScreen
import org.quixalert.br.view.pages.login.LoginScreen
import org.quixalert.br.view.pages.login.RegisterScreen
import org.quixalert.br.view.pages.login.SignInScreen
import org.quixalert.br.model.UserRegistrationData
import org.quixalert.br.view.pages.login.RegisterStepTwoScreen

@Composable
@Preview
fun App() {
    var currentScreen by remember { mutableStateOf("login") }
    var registrationData by remember { mutableStateOf<UserRegistrationData?>(null) }

    AppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background)
        ) {
            when (currentScreen) {
                "login" -> LoginScreen(
                    onRegisterClick = { currentScreen = "register" },
                    onLoginClick = { currentScreen = "signin" }
                )
                "signin" -> SignInScreen()
                "register" -> RegisterScreen(
                    onNextStep = { data ->
                        registrationData = data
                        currentScreen = "register_step_two"
                    }
                )
                "register_step_two" -> RegisterStepTwoScreen(
                    initialData = registrationData ?: UserRegistrationData(),
                    onRegisterComplete = { finalData ->
                        // Handle registration completion
                        currentScreen = "login"
                    }
                )
            }
        }
    }
}