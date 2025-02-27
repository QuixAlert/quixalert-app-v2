package org.quixalert.br

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.quixalert.br.MockData.biddings
import org.quixalert.br.domain.model.AdoptionT
import org.quixalert.br.domain.model.Animal
import org.quixalert.br.domain.model.Document
import org.quixalert.br.domain.model.User
import org.quixalert.br.domain.model.UserRegistrationData
import org.quixalert.br.presentation.pages.adoptions.AdoptionFormScreen
import org.quixalert.br.presentation.pages.adoptions.AdoptionScreen
import org.quixalert.br.presentation.pages.adoptions.AdoptionSolicitationScreen
import org.quixalert.br.presentation.pages.adoptions.ChatScreen
import org.quixalert.br.presentation.pages.adoptions.ChatScreenDocumentation
import org.quixalert.br.presentation.pages.animal.AnimalDetailsScreen
import org.quixalert.br.presentation.pages.documentsSolicitationScreen.DocumentationScreen
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
import org.quixalert.br.presentation.pages.profile.ProfileViewModel
import org.quixalert.br.presentation.pages.reports.ReportScreen
import org.quixalert.br.presentation.pages.reportsSolicitationScreen.ReportsSolicitationScreen
import org.quixalert.br.services.FirebaseAuthService
import org.quixalert.br.view.pages.login.LoginScreen

@Composable
fun RenderScreen(
    currentScreen: String,
    lastScreen: String,
    currentUser: User?,
    registrationData: UserRegistrationData?,
    firebaseAuthService: FirebaseAuthService,
    selectedAnimal: Animal?,
    selectedAdoption: AdoptionT?,
    selectedDocument: Document?,
    selectedReportId: String?,
    isDarkTheme: MutableState<Boolean>,
    onScreenChange: (String) -> Unit,
    onLastScreenChange: (String) -> Unit,
    onUserUpdate: (User?) -> Unit,
    onRegistrationDataUpdate: (UserRegistrationData?) -> Unit,
    onAnimalSelected: (Animal?) -> Unit,
    onAdoptionSelected: (AdoptionT?) -> Unit,
    onDocumentSelected: (Document?) -> Unit,
    onReportSelected: (String?) -> Unit,
    loginViewModel: LoginViewModel,
    profileViewModel: ProfileViewModel,
    scope: CoroutineScope,
    context: android.content.Context
) {
    when (currentScreen) {
        "login" -> LoginScreen(
            onRegisterClick = { onScreenChange("register") },
            onLoginClick = { onScreenChange("signin") }
        )

        "signin" -> SignInScreen(
            onLoginSuccess = { userId ->
                scope.launch {
                    try {
                        val firestore = FirebaseFirestore.getInstance()
                        val userDoc = firestore.collection("users")
                            .document(userId)
                            .get()
                            .await()

                        if (userDoc.exists()) {
                            onUserUpdate(
                                User(
                                    id = userId,
                                    name = userDoc.getString("name") ?: "Usuário Anônimo",
                                    greeting = "Bem-vindo de volta!",
                                    profileImage = userDoc.getString("profileImage")
                                        ?: "https://s2-techtudo.glbimg.com/default_profile.png"
                                )
                            )
                        }
                        onScreenChange("home")
                    } catch (e: Exception) {
                        Log.e("SignIn", "Error fetching user data", e)
                    }
                }
            }
        )

        "register" -> RegisterScreen(
            onNextStep = { data ->
                onRegistrationDataUpdate(data)
                onScreenChange("register_step_two")
            }
        )

        "register_step_two" -> RegisterStepTwoScreen(
            initialData = registrationData ?: UserRegistrationData(),
            onRegisterComplete = {
                val fbUser = FirebaseAuth.getInstance().currentUser
                if (fbUser != null) {
                    onUserUpdate(
                        User(
                            id = fbUser.uid,
                            name = fbUser.displayName ?: "Usuário Anônimo",
                            greeting = "Bem-vindo!",
                            profileImage = fbUser.photoUrl?.toString()
                                ?: "https://s2-techtudo.glbimg.com/default_profile.png"
                        )
                    )
                    onScreenChange("home")
                } else {
                    onScreenChange("login")
                }
            }
        )

        "home" -> {
            if (currentUser != null) {
                HomeScreen(
                    user = currentUser,
                    onNotificationClick = { onScreenChange("notification") },
                    onClick = {
                        onScreenChange("pet_details")
                        onLastScreenChange("home")
                    }
                )
            } else {
                onScreenChange("login")
            }
        }

        "notification" -> NotificationScreen()

        "profile" -> {
            currentUser?.let { user ->
                ProfileScreen(
                    biddings = biddings,
                    onBackClick = { onScreenChange("home") },
                    onEditProfileClick = { onScreenChange("edit_profile") },
                    onBiddingClick = { },
                    onReportClick = { report -> 
                        onReportSelected(report.id)
                        onScreenChange("report_details")
                    },
                    isDarkThemeEnabled = isDarkTheme.value,
                    onThemeToggle = { isDarkTheme.value = it },
                    onExitClick = {
                        FirebaseAuth.getInstance().signOut()
                        loginViewModel.resetLoginState()
                        onUserUpdate(null)
                        onScreenChange("login")
                    },
                    onFaqCLick = { onScreenChange("faq") },
                    onAdoptionClick = { adoption ->
                        onAdoptionSelected(adoption)
                        onScreenChange("solicitation")
                    },
                    onDocumentClick = { document ->
                        onDocumentSelected(document)
                        onScreenChange("documentation")
                    },
                    user = user,
                    firebaseAuthService = firebaseAuthService,
                    onProfileImageChange = { uri ->
                        onUserUpdate(user)
                        profileViewModel.updateProfileImage(context, uri, user.id) { success ->
                            if (success) {
                                scope.launch {
                                    try {
                                        val firestore = FirebaseFirestore.getInstance()
                                        val userDoc = firestore.collection("users")
                                            .document(user.id)
                                            .get()
                                            .await()

                                        if (userDoc.exists()) {
                                            onUserUpdate(
                                                user.copy(
                                                    profileImage = userDoc.getString("profileImage")
                                                        ?: user.profileImage
                                                )
                                            )
                                        }
                                    } catch (e: Exception) {
                                        Log.e("Profile", "Error updating profile image", e)
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }

        "news" -> NewsScreen()

        "animals" -> AdoptionScreen(
            onDonateClick = { onScreenChange("donate") },
            onDetailsClick = {
                onScreenChange("pet_details")
                onLastScreenChange("animals")
            }
        )

        "donate" -> DonationScreen(
            onBackClick = { onScreenChange("animals") },
            onFormClick = { onScreenChange("animals") }
        )

        "reports_solicitation" -> currentUser?.let {
            ReportsSolicitationScreen(
                onBackClick = { onScreenChange("home") },
                onFormClick = { onScreenChange("animals") },
                user = it
            )
        }

        "documents" -> currentUser?.let {
            DocumentsSolicitationScreen(
                onBackClick = { onScreenChange("home") },
                onFormClick = { onScreenChange("home") },
                user = it
            )
        }

        "emergency" -> EmergencyNumbersScreen(
            onBackClick = { onScreenChange("home") }
        )

        "pet_details" -> AnimalDetailsScreen(
            selectedAnimal = selectedAnimal,
            onBackClick = { onScreenChange(lastScreen) },
            onFormClick = { onScreenChange("form_adote") }
        )

        "form_adote" -> AdoptionFormScreen(
            onBackClick = { animal ->
                onAnimalSelected(animal)
                onScreenChange("pet_details")
            },
            onFormClick = { onScreenChange("animals") },
            firebaseAuthService = firebaseAuthService
        )

        "report_details" -> {
            selectedReportId?.let { reportId ->
                Log.d("RenderScreen", "Navigating to report details with ID: $reportId")
                ReportScreen(
                    reportId = reportId,
                    onBackClick = { onScreenChange("profile") },
                    onNavigate = { screen -> onScreenChange(screen) }
                )
            } ?: run {
                Log.e("RenderScreen", "No report ID provided")
                onScreenChange("profile")
            }
        }

        "faq" -> FaqScreen()

        "solicitation" -> selectedAdoption?.let {
            if (currentUser != null) {
                AdoptionSolicitationScreen(
                    adoption = it,
                    onBackClick = { onScreenChange("profile") },
                    onOpenChatClick = { onScreenChange("chat") },
                    user = currentUser
                )
            }
        }

        "documentation" -> selectedDocument?.let {
            if (currentUser != null) {
                DocumentationScreen(
                    document = it,
                    onBackClick = { onScreenChange("profile") },
                    onOpenChatClick = { onScreenChange("chatDocumentation") },
                    user = currentUser,
                )
            }
        }

        "chat" -> selectedAdoption?.let {
            if(currentUser != null){
                ChatScreen(
                    adoption = it,
                    onBackClick = { onScreenChange("solicitation") },
                    user = currentUser
                )
            }
        }

        "chatDocumentation" -> selectedDocument?.let {
            if(currentUser != null){
                ChatScreenDocumentation(
                    document = it,
                    onBackClick = { onScreenChange("documentation") },
                    user = currentUser
                )
            }
        }
    }
}