package org.quixalert.br.presentation.pages.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.UserRegistrationData
import org.quixalert.br.services.FirebaseAuthService
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
    private val firebaseAuthService: FirebaseAuthService, // Adiciona o serviço de autenticação
    private val userRegistrationService: UserRegistrationService
) : ViewModel() {

    private val _stepTwoUiState = MutableStateFlow(RegisterStepTwoUiState())
    val stepTwoUiState: StateFlow<RegisterStepTwoUiState> get() = _stepTwoUiState

    fun completeRegistration(email: String, password: String, userData: UserRegistrationData) {
        _stepTwoUiState.value = RegisterStepTwoUiState(isLoading = true, userRegistrationData = userData)

        viewModelScope.launch {
            try {
                // Registra o usuário no Firebase Auth
                val firebaseUser = firebaseAuthService.registerUser(email, password, name = userData.name, profileImage = userData.profileImage.toString(), phone = userData.phone)


                if (firebaseUser != null) {
                    val userWithUid = userData.copy(id = firebaseUser.uid) // Atribui o UID gerado do Firebase

                    // Se o registro no Firebase for bem-sucedido, chama o serviço para salvar no Firestore
                    userRegistrationService.updateById(userWithUid.id, userWithUid)


                    // Atualiza o estado com sucesso
                    _stepTwoUiState.value = RegisterStepTwoUiState(
                        isLoading = false,
                        isSuccess = true,
                        userRegistrationData = userWithUid
                    )
                } else {
                    // Se o Firebase Auth falhar
                    _stepTwoUiState.value = RegisterStepTwoUiState(
                        isLoading = false,
                        isSuccess = false,
                        errorMessage = "Erro ao registrar usuário no Firebase.",
                        userRegistrationData = userData
                    )
                }
            } catch (e: Exception) {
                // Caso ocorra um erro
                _stepTwoUiState.value = RegisterStepTwoUiState(
                    isLoading = false,
                    isSuccess = false,
                    errorMessage = "Erro ao completar registro: ${e.message}",
                    userRegistrationData = userData
                )
            }
        }
    }

    fun resetRegisterState() {
        _stepTwoUiState.value = RegisterStepTwoUiState()
    }
}
