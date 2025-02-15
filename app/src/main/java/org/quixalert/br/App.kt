package org.quixalert.br

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import org.quixalert.br.MockData.adoptions
import org.quixalert.br.MockData.biddings
import org.quixalert.br.MockData.reports
import org.quixalert.br.domain.model.AdoptionT
import org.quixalert.br.domain.model.Animal
import org.quixalert.br.domain.model.User
import org.quixalert.br.domain.model.UserRegistrationData
import org.quixalert.br.presentation.components.FloatingMenu
import org.quixalert.br.presentation.components.HeaderSection
import org.quixalert.br.presentation.components.NavigationBarM3
import org.quixalert.br.presentation.pages.adoptions.AdoptionFormScreen
import org.quixalert.br.presentation.pages.adoptions.AdoptionScreen
import org.quixalert.br.presentation.pages.adoptions.AdoptionSolicitationScreen
import org.quixalert.br.presentation.pages.adoptions.ChatScreen
import org.quixalert.br.presentation.pages.animal.AnimalDetailsScreen
import org.quixalert.br.presentation.pages.documentsSolicitationScreen.DocumentsSolicitationScreen
import org.quixalert.br.presentation.pages.donation.DonationScreen
import org.quixalert.br.presentation.pages.emergencyNumbers.EmergencyNumbersScreen
import org.quixalert.br.presentation.pages.faq.FaqScreen
import org.quixalert.br.presentation.pages.home.HomeScreen
import org.quixalert.br.presentation.pages.login.LoginViewModel
import org.quixalert.br.presentation.pages.login.RegisterScreen
import org.quixalert.br.presentation.pages.login.RegisterStepTwoScreen
import org.quixalert.br.presentation.pages.login.SignInScreen
import org.quixalert.br.presentation.pages.news.NewsScreen
import org.quixalert.br.presentation.pages.notification.NotificationScreen
import org.quixalert.br.presentation.pages.profile.ProfileScreen
import org.quixalert.br.presentation.pages.reports.ReportScreen
import org.quixalert.br.presentation.pages.reportsSolicitationScreen.ReportsSolicitationScreen
import org.quixalert.br.services.FirebaseAuthService
import org.quixalert.br.view.pages.login.LoginScreen

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun App() {
    val loginViewModel: LoginViewModel = viewModel()
    val firebaseAuthService = FirebaseAuthService(FirebaseAuth.getInstance())
    var currentScreen by remember { mutableStateOf("profile") }
    var currentUser by remember { mutableStateOf<User?>(null) }
    var registrationData by remember { mutableStateOf<UserRegistrationData?>(null) }
    var isFloatingMenuVisible by remember { mutableStateOf(false) }
    var selectedAnimal by remember { mutableStateOf<Animal?>(null) }
    var selectedAdoption by remember { mutableStateOf<AdoptionT?>(null) }
    val context = LocalContext.current
    val currentDarkTheme = false;
    val isDarkTheme = remember { mutableStateOf(currentDarkTheme) }
    val systemUiController = rememberSystemUiController()

    // Set system bar colors (example uses blue for both themes)
    systemUiController.setSystemBarsColor(color = Color.Blue)

    // Check if there is a logged-in user on start
    LaunchedEffect(Unit) {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null) {
            // If user is logged in, update currentUser state and navigate to home
            currentUser = User(
                id = user.uid,
                name = user.displayName ?: "Usuário Anônimo",
                greeting = "Bem-vindo de volta!",
                profileImage = user.photoUrl?.toString() ?: "https://s2-techtudo.glbimg.com/default_profile.png"
            )
            currentScreen = "home"
            Log.d("UserInfo", "UID do usuário logado: ${user.uid}")
            Log.d("UserInfo", "Nome do usuário: ${user.displayName ?: "Usuário Anônimo"}")
        }
    }

    QuixalertTheme(darkTheme = isDarkTheme.value) {
        val modifierTopBarBlur = if (isFloatingMenuVisible) {
            Modifier.background(Color.Black.copy(alpha = 0.5f))
        } else {
            Modifier.background(Color.Transparent)
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),

            topBar = {
                if (currentScreen == "") {
                    Column(
                        modifier = modifierTopBarBlur
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.background)
                            .padding(top = 24.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        HeaderSection()
                    }
                }
            },

            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(top = 32.dp)
                ) {
                    when (currentScreen) {
                        "login" -> LoginScreen(
                            onRegisterClick = { currentScreen = "register" },
                            onLoginClick = { currentScreen = "signin" }
                        )
                        "signin" -> SignInScreen(
                            onLoginSuccess = { userId ->
                                // When login succeeds, update the currentUser state
                                val auth = FirebaseAuth.getInstance()
                                val fbUser = auth.currentUser
                                if (fbUser != null) {
                                    currentUser = User(
                                        id = fbUser.uid,
                                        name = fbUser.displayName ?: "Usuário Anônimo",
                                        greeting = "Bem-vindo de volta!",
                                        profileImage = fbUser.photoUrl?.toString() ?: "https://s2-techtudo.glbimg.com/default_profile.png"
                                    )
                                }
                                currentScreen = "home"
                            }
                        )
                        "register" -> RegisterScreen(
                            onNextStep = { data ->
                                registrationData = data
                                currentScreen = "register_step_two"
                            }
                        )
                        "register_step_two" -> RegisterStepTwoScreen(
                            initialData = registrationData ?: UserRegistrationData(),
                            onRegisterComplete = {
                                // After registration, attempt to get the current Firebase user.
                                val auth = FirebaseAuth.getInstance()
                                val fbUser = auth.currentUser
                                if (fbUser != null) {
                                    currentUser = User(
                                        id = fbUser.uid,
                                        name = fbUser.displayName ?: "Usuário Anônimo",
                                        greeting = "Bem-vindo!",
                                        profileImage = fbUser.photoUrl?.toString() ?: "https://s2-techtudo.glbimg.com/default_profile.png"
                                    )
                                    currentScreen = "home"
                                } else {
                                    // If registration did not automatically sign in, you can redirect to the signin screen.
                                    currentScreen = "login"
                                }
                            }
                        )
                        "home" -> {
                            if (currentUser != null) {
                                HomeScreen(
                                    user = currentUser!!,
                                    onNotificationClick = { currentScreen = "notification" }
                                )
                            } else {
                                // If for some reason the user state is null, fallback to login.
                                currentScreen = "login"
                            }
                        }
                        "notification" -> NotificationScreen()
                        "profile" -> ProfileScreen(
                            reports = reports,
                            biddings = biddings,
                            adoptions = adoptions,
                            onBackClick = { currentScreen = "home" },
                            onEditProfileClick = { currentScreen = "edit_profile" },
                            onBiddingClick = { },
                            onReportClick = { currentScreen = "report_details" },
                            isDarkThemeEnabled = isDarkTheme.value,
                            onThemeToggle = { isDarkTheme.value = it },
                            onExitClick = {
                                FirebaseAuth.getInstance().signOut()
                                loginViewModel.resetLoginState()
                                currentUser = null
                                currentScreen = "login"
                            },
                            onFaqCLick = {
                                currentScreen = "faq"
                            },
                            onAdoptionClick = { adoption ->
                                currentScreen = "solicitation"
                                selectedAdoption = adoption
                            },
                            firebaseAuthService = firebaseAuthService
                        )
                        "news" -> NewsScreen()
                        "animals" -> AdoptionScreen(
                            onDonateClick = { currentScreen = "donate" },
                            onDetailsClick = { currentScreen = "pet_details" }
                        )
                        "donate" -> DonationScreen(
                            onBackClick = { currentScreen = "animals" },
                            onFormClick = { currentScreen = "animals" }
                        )
                        "reports_solicitation" -> ReportsSolicitationScreen(
                            onBackClick = { currentScreen = "home" },
                            onFormClick = { currentScreen = "animals" }
                        )
                        "documents" -> DocumentsSolicitationScreen(
                            onBackClick = { currentScreen = "home" },
                            onFormClick = { currentScreen = "home" }
                        )
                        "emergency" -> EmergencyNumbersScreen(
                            onBackClick = { currentScreen = "home" }
                        )
                        "pet_details" -> AnimalDetailsScreen(
                            selectedAnimal = selectedAnimal,
                            onBackClick = { currentScreen = "animals" },
                            onFormClick = { currentScreen = "form_adote" }
                        )
                        "form_adote" -> AdoptionFormScreen(
                            user = mockUser,
                            onBackClick = { animal ->
                                selectedAnimal = animal
                                currentScreen = "pet_details"
                            },
                            onFormClick = { currentScreen = "animals" }
                        )
                        "report_details" -> ReportScreen()
                        "faq" -> FaqScreen()
                        "solicitation" -> selectedAdoption?.let {
                            AdoptionSolicitationScreen(
                                adoption = it,
                                onBackClick = { currentScreen = "profile" },
                                onOpenChatClick = { currentScreen = "chat" }
                            )
                        }
                        "chat" -> selectedAdoption?.let {
                            ChatScreen(
                                adoption = it,
                                onBackClick = { currentScreen = "solicitation" }
                            )
                        }                    }

                    if (isFloatingMenuVisible) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f))
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    isFloatingMenuVisible = false
                                }
                        )
                    }
                }
            },

            bottomBar = {
                if (currentScreen == "home" || currentScreen == "profile" || currentScreen == "notification" || currentScreen == "news" || currentScreen == "animals" || currentScreen == "faq" || currentScreen == "solicitation") {
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
