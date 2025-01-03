package org.quixalert.br

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.quixalert.br.MockData.adoptions
import org.quixalert.br.MockData.biddings
import org.quixalert.br.MockData.reports
import org.quixalert.br.domain.model.UserRegistrationData
import org.quixalert.br.presentation.components.FloatingMenu
import org.quixalert.br.presentation.components.HeaderSection
import org.quixalert.br.presentation.components.NavigationBarM3
import org.quixalert.br.presentation.pages.adoptions.AdoptionFormScreen
import org.quixalert.br.presentation.pages.adoptions.AdoptionScreen
import org.quixalert.br.presentation.pages.animal.AnimalDetailsScreen
import org.quixalert.br.presentation.pages.animal.mockPetDetail
import org.quixalert.br.presentation.pages.documentsSolicitationScreen.DocumentsSolicitationScreen
import org.quixalert.br.presentation.pages.donation.DonationScreen
import org.quixalert.br.presentation.pages.emergencyNumbers.EmergencyNumbersScreen
import org.quixalert.br.presentation.pages.login.RegisterScreen
import org.quixalert.br.presentation.pages.login.RegisterStepTwoScreen
import org.quixalert.br.presentation.pages.login.SignInScreen
import org.quixalert.br.presentation.pages.newsScreen.newsScreen
import org.quixalert.br.presentation.pages.notification.NotificationScreen
import org.quixalert.br.presentation.pages.profile.ProfileScreen
import org.quixalert.br.presentation.pages.reports.ReportScreen
import org.quixalert.br.presentation.pages.reportsSolicitationScreen.ReportsSolicitationScreen
import org.quixalert.br.presentation.ui.theme.AppTheme
import org.quixalert.br.presentation.pages.home.HomeScreen
import org.quixalert.br.view.pages.login.LoginScreen

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf("login") }
    var registrationData by remember { mutableStateOf<UserRegistrationData?>(null) }
    var isFloatingMenuVisible by remember { mutableStateOf(false) }

    AppTheme {
        val modifierTopBarBlur = if (isFloatingMenuVisible) {
            Modifier.background(Color.Black.copy(alpha = 0.5f))
        } else {
            Modifier.background(Color.Transparent)
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background),

            topBar = {
                if (currentScreen == "login") {
                    Column(
                        modifier = modifierTopBarBlur
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        HeaderSection()
                    }
                }
            },

            content = { innerPadding ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)) {
                    when (currentScreen) {
                        "login" -> LoginScreen(
                            onRegisterClick = { currentScreen = "register" },
                            onLoginClick = { currentScreen = "signin" }
                        )
                        "signin" -> SignInScreen(
                            onSignInClick = { currentScreen = "home" }
                        )
                        "register" -> RegisterScreen(
                            onNextStep = { data ->
                                registrationData = data
                                currentScreen = "register_step_two"
                            }
                        )
                        "register_step_two" -> RegisterStepTwoScreen(
                            initialData = registrationData ?: UserRegistrationData(),
                            onRegisterComplete = { currentScreen = "login" }
                        )
                        "home" -> HomeScreen(
                            user = mockUser,
                            localNews = mockLocalNews,
                            globalNews = mockGlobalNews,
                            pets = mockPets,
                            onNotificationClick = { currentScreen = "notification" }
                        )
                        "notification" -> NotificationScreen()
                        "profile" -> ProfileScreen(
                            user = mockUser,
                            reports = reports,
                            biddings = biddings,
                            adoptions = adoptions,
                            onBackClick = { currentScreen = "home" },
                            onMenuClick = { isFloatingMenuVisible = !isFloatingMenuVisible },
                            onEditProfileClick = { currentScreen = "edit_profile" },
                            onBiddingClick = { currentScreen = "bidding_pdf" },
                            onReportClick = { currentScreen = "report_details" }
                        )
                        "news" -> newsScreen()
                        "animals" -> AdoptionScreen(
                            onDonateClick = { currentScreen = "donate" },
                            onDetailsClick = { currentScreen = "pet_details" }
                        )
                        "donate" -> DonationScreen(
                            onBackClick = { currentScreen = "animals" },
                            onMenuClick = { isFloatingMenuVisible = !isFloatingMenuVisible }
                        )
                        "reports_solicitation" -> ReportsSolicitationScreen(
                            onBackClick = { currentScreen = "home" },
                            onMenuClick = { isFloatingMenuVisible = !isFloatingMenuVisible }
                        )
                        "documents" -> DocumentsSolicitationScreen(
                            onBackClick = { currentScreen = "home" },
                            onMenuClick = { isFloatingMenuVisible = !isFloatingMenuVisible }
                        )
                        "emergency" -> EmergencyNumbersScreen(
                            onBackClick = { currentScreen = "home" },
                            onMenuClick = { isFloatingMenuVisible = !isFloatingMenuVisible }
                        )
                        "pet_details" -> AnimalDetailsScreen(
                            onBackClick = { currentScreen = "animals" },
                            onMenuClick = { isFloatingMenuVisible = !isFloatingMenuVisible },
                            onFormClick = { currentScreen = "form_adote" }
                        )
                        "form_adote" -> AdoptionFormScreen(
                            pet = mockPetDetail,
                            onBackClick = { currentScreen = "pet_details" }
                        )
                        "report_details" -> ReportScreen()
                    }

                    if (isFloatingMenuVisible) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f))
                        )
                    }
                }
            },

            bottomBar = {
                if (currentScreen == "home" || currentScreen == "profile" || currentScreen == "notification" || currentScreen == "news" || currentScreen == "animals") {
                    Column {
                        if (isFloatingMenuVisible) {
                            FloatingMenu(
                                modifier = Modifier.padding(bottom = 18.dp),
                                onReportClick = {
                                    currentScreen = "reports_solicitation"
                                    isFloatingMenuVisible = false
                                },
                                onDocumentClick = {
                                    currentScreen = "documents"
                                    isFloatingMenuVisible = false
                                },
                                onEmergencyClick = {
                                    currentScreen = "emergency"
                                    isFloatingMenuVisible = false
                                }
                            )
                        }
                        NavigationBarM3(
                            onPlusClick = { isFloatingMenuVisible = !isFloatingMenuVisible },
                            onOtherCLick = { route ->
                                currentScreen = route
                                isFloatingMenuVisible = false
                            }
                        )
                    }
                }
            }
        )
    }
}
