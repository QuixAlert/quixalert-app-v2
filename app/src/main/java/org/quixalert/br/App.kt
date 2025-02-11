package org.quixalert.br

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.quixalert.br.MockData.adoptions
import org.quixalert.br.MockData.biddings
import org.quixalert.br.MockData.reports
import org.quixalert.br.domain.model.Animal
import org.quixalert.br.domain.model.UserRegistrationData
import org.quixalert.br.presentation.components.FloatingMenu
import org.quixalert.br.presentation.components.HeaderSection
import org.quixalert.br.presentation.components.NavigationBarM3
import org.quixalert.br.presentation.pages.adoptions.AdoptionFormScreen
import org.quixalert.br.presentation.pages.adoptions.AdoptionScreen
import org.quixalert.br.presentation.pages.animal.AnimalDetailsScreen
import org.quixalert.br.presentation.pages.documentsSolicitationScreen.DocumentsSolicitationScreen
import org.quixalert.br.presentation.pages.donation.DonationScreen
import org.quixalert.br.presentation.pages.emergencyNumbers.EmergencyNumbersScreen
import org.quixalert.br.presentation.pages.faq.FaqScreen
import org.quixalert.br.presentation.pages.home.HomeScreen
import org.quixalert.br.presentation.pages.login.RegisterScreen
import org.quixalert.br.presentation.pages.login.RegisterStepTwoScreen
import org.quixalert.br.presentation.pages.login.SignInScreen
import org.quixalert.br.presentation.pages.news.NewsScreen
import org.quixalert.br.presentation.pages.notification.NotificationScreen
import org.quixalert.br.presentation.pages.profile.ProfileScreen
import org.quixalert.br.presentation.pages.reports.ReportScreen
import org.quixalert.br.presentation.pages.reportsSolicitationScreen.ReportsSolicitationScreen
import org.quixalert.br.view.pages.login.LoginScreen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import com.google.firebase.auth.FirebaseAuth
import org.quixalert.br.domain.model.Adoption
import org.quixalert.br.domain.model.User
import org.quixalert.br.presentation.pages.adoptions.AdoptionSolicitationScreen


@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun App() {
    var currentScreen by remember { mutableStateOf("login") }
    var currentUser by remember { mutableStateOf<User?>(null) }
    var registrationData by remember { mutableStateOf<UserRegistrationData?>(null) }
    var isFloatingMenuVisible by remember { mutableStateOf(false) }
    var selectedAnimal by remember { mutableStateOf<Animal?>(null) }
    var selectedAdoption by remember { mutableStateOf<Adoption?>(null) }
    val context = LocalContext.current
    val currentDarkTheme = false;
    val isDarkTheme = remember { mutableStateOf(currentDarkTheme) }
    val systemUiController = rememberSystemUiController()
    if(isDarkTheme.value){
        systemUiController.setSystemBarsColor(
            color = Color.Blue
        )
    }else{
        systemUiController.setSystemBarsColor(
            color = Color.Blue
        )
    }
    // Verificar usuário logado
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    if (user != null) {
        // Se o usuário estiver logado, obtenha suas informações
        currentUser = User(
            id = user.uid,
            name = user.displayName ?: "Usuário Anônimo",
            greeting = "Bem-vindo de volta!",
            profileImage = user.photoUrl?.toString() ?: "https://randomuser.me/api/portraits/men/1.jpg"
        )
        currentScreen = "home" // Direciona para a tela inicial
        Log.d("UserInfo", "UID do usuário logado: ${user.uid}")
        Log.d("UserInfo", "Nome do usuário: ${user.displayName ?: "Usuário Anônimo"}")
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
                            onLoginSuccess  = { currentScreen = "home" }
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
                        "home" -> {
                            if (currentUser != null) {
                                HomeScreen(
                                    user = currentUser!!, // Agora com a verificação de nulidade antes de acessar
                                    onNotificationClick = { currentScreen = "notification" }
                                )
                            } else {
                                // Exibir uma tela alternativa (como uma tela de login)
                                LoginScreen(
                                    onRegisterClick = { currentScreen = "register" },
                                    onLoginClick = { currentScreen = "signin" }
                                )
                            }
                        }
                        "notification" -> NotificationScreen()
                        "profile" -> ProfileScreen(
                            user = mockUser,
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
                                currentScreen = "login"
                                (context as? Activity)?.finish()
                            },
                            onFaqCLick = {
                                currentScreen = "faq"
                            },
                            onAdoptionClick = { adoption ->
                                currentScreen = "solicitation"
                                selectedAdoption = adoption
                            }
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
                            onBackClick = { currentScreen = "home" },
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
                                currentScreen = "pet_details" },
                            onFormClick = { currentScreen = "animals" }
                        )
                        "report_details" -> ReportScreen()
                        "faq" -> FaqScreen()
                        "solicitation" -> selectedAdoption?.let { it1 -> AdoptionSolicitationScreen(adoption = it1) }
                    }

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
                if (currentScreen == "home" || currentScreen == "profile" || currentScreen == "notification" || currentScreen == "news" || currentScreen == "animals" || currentScreen == "faq") {
                    Column {
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
