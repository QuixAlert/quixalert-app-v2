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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.quixalert.br.domain.model.AdoptionT
import org.quixalert.br.domain.model.Animal
import org.quixalert.br.domain.model.User
import org.quixalert.br.domain.model.UserRegistrationData
import org.quixalert.br.presentation.components.FloatingMenu
import org.quixalert.br.presentation.components.HeaderSection
import org.quixalert.br.presentation.components.NavigationBarM3
import org.quixalert.br.presentation.pages.login.LoginViewModel
import org.quixalert.br.presentation.pages.profile.ProfileViewModel
import org.quixalert.br.services.FirebaseAuthService

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun App() {
    val loginViewModel: LoginViewModel = viewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var currentScreen by remember { mutableStateOf("login") }
    var currentUser by remember { mutableStateOf<User?>(null) }
    var registrationData by remember { mutableStateOf<UserRegistrationData?>(null) }
    var isFloatingMenuVisible by remember { mutableStateOf(false) }
    var selectedAnimal by remember { mutableStateOf<Animal?>(null) }
    var selectedAdoption by remember { mutableStateOf<AdoptionT?>(null) }
    val firebaseAuthService = FirebaseAuthService(FirebaseAuth.getInstance())

    val currentDarkTheme = false
    val isDarkTheme = remember { mutableStateOf(currentDarkTheme) }
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.Blue)

    LaunchedEffect(Unit) {
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()
        val user = auth.currentUser

        if (user != null) {
            try {
                val userDoc = firestore.collection("users")
                    .document(user.uid)
                    .get()
                    .await()

                if (userDoc.exists()) {
                    currentUser = User(
                        id = user.uid,
                        name = userDoc.getString("name") ?: "Usuário Anônimo",
                        greeting = "Bem-vindo de volta!",
                        profileImage = userDoc.getString("profileImage")
                            ?: "https://s2-techtudo.glbimg.com/default_profile.png"
                    )
                    currentScreen = "home"
                    Log.d("UserInfo", "UID do usuário logado: ${user.uid}")
                    Log.d("UserInfo", "Nome do usuário: ${userDoc.getString("name") ?: "Usuário Anônimo"}")
                }
            } catch (e: Exception) {
                Log.e("UserInfo", "Error fetching user data", e)
            }
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
                            .background(MaterialTheme.colorScheme.background)
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
                        .background(MaterialTheme.colorScheme.background)
                        .padding(top = 32.dp)
                ) {
                    RenderScreen(
                        currentScreen = currentScreen,
                        currentUser = currentUser,
                        registrationData = registrationData,
                        firebaseAuthService = firebaseAuthService,
                        selectedAnimal = selectedAnimal,
                        selectedAdoption = selectedAdoption,
                        isDarkTheme = isDarkTheme,
                        onScreenChange = { currentScreen = it },
                        onUserUpdate = { currentUser = it },
                        onRegistrationDataUpdate = { registrationData = it },
                        onAnimalSelected = { selectedAnimal = it },
                        onAdoptionSelected = { selectedAdoption = it },
                        loginViewModel = loginViewModel,
                        profileViewModel = profileViewModel,
                        scope = scope,
                        context = context
                    )

                    if (isFloatingMenuVisible) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f))
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { isFloatingMenuVisible = false }
                        )
                    }
                }
            },
            bottomBar = {
                if (currentScreen in listOf(
                        "home", "profile", "notification", "news", "animals", "faq", "solicitation"
                    )
                ) {
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