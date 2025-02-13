package org.quixalert.br.presentation.pages.register

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.UserRegistrationData
import org.quixalert.br.services.FirebaseAuthService
import org.quixalert.br.services.FirebaseStorageService
import org.quixalert.br.services.UserRegistrationService
import javax.inject.Inject

data class RegisterStepTwoUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val userRegistrationData: UserRegistrationData? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService,
    private val firebaseStorageService: FirebaseStorageService,
    private val userRegistrationService: UserRegistrationService
) : ViewModel() {

    private val _stepTwoUiState = MutableStateFlow(RegisterStepTwoUiState())
    val stepTwoUiState: StateFlow<RegisterStepTwoUiState> get() = _stepTwoUiState

    fun completeRegistration(context: Context, email: String, password: String, userData: UserRegistrationData, imageUri: Uri?) {
        _stepTwoUiState.value = RegisterStepTwoUiState(isLoading = true, userRegistrationData = userData)

        viewModelScope.launch {
            try {

                var finalProfileImage = userData.profileImage?.toString() ?: ""

                if (imageUri != null) {
                    firebaseStorageService.uploadImageToFirebase(context, imageUri, email) { uploadedImageUrl ->
                        if (uploadedImageUrl != null) {
                            finalProfileImage = uploadedImageUrl // Atualiza a URL da imagem
                        }

                        registerUserWithFirebase(email, password, userData, finalProfileImage)
                    }
                } else {
                    registerUserWithFirebase(email, password, userData, finalProfileImage)
                }
            } catch (e: Exception) {
                _stepTwoUiState.value = RegisterStepTwoUiState(
                    isLoading = false,
                    isSuccess = false,
                    errorMessage = "Erro ao completar registro: ${e.message}",
                    userRegistrationData = userData
                )
            }
        }
    }
    private fun saveUserProfileImage(photoUrl: String) {
        firebaseAuthService.updateUserProfile(photoUrl) { success ->
            if (success) {
                Log.d("ProfileViewModel", "Imagem de perfil atualizada com sucesso.")
            } else {
                Log.e("ProfileViewModel", "Falha ao atualizar a imagem do perfil.")
            }
        }
    }


    private fun registerUserWithFirebase(email: String, password: String, userData: UserRegistrationData, profileImageUrl: String) {
        viewModelScope.launch {
            val firebaseUser = firebaseAuthService.registerUser(email, password, userData.name,
                profileImageUrl, userData.phone)

            if (firebaseUser != null) {
                val userWithUid = userData.copy(id = firebaseUser.uid, profileImage = profileImageUrl)

                saveUserProfileImage(profileImageUrl)

                userRegistrationService.updateById(userWithUid.id, userWithUid)

                _stepTwoUiState.value = RegisterStepTwoUiState(
                    isLoading = false,
                    isSuccess = true,
                    userRegistrationData = userWithUid
                )
            } else {
                _stepTwoUiState.value = RegisterStepTwoUiState(
                    isLoading = false,
                    isSuccess = false,
                    errorMessage = "Erro ao registrar usuário no Firebase.",
                    userRegistrationData = userData
                )
            }
        }
    }
}
