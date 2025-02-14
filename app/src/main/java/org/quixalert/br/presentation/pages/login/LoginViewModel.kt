package org.quixalert.br.presentation.pages.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.UserRegistrationData
import org.quixalert.br.services.FirebaseAuthService
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val userRegistrationData: UserRegistrationData? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService // Serviço de autenticação
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> get() = _loginUiState

    // Função para fazer o login
    fun loginUser(email: String, password: String) {
        _loginUiState.value = LoginUiState(isLoading = true)

        viewModelScope.launch {
            try {
                // Tenta fazer login com as credenciais fornecidas
                val firebaseUser = firebaseAuthService.loginUser(email, password)

                if (firebaseUser != null) {
                    // Login bem-sucedido, passa o UID do usuário para o estado
                    _loginUiState.value = LoginUiState(
                        isLoading = false,
                        isSuccess = true,
                        userRegistrationData = UserRegistrationData(id = firebaseUser.uid)
                    )
                } else {
                    // Se o login falhar
                    _loginUiState.value = LoginUiState(
                        isLoading = false,
                        isSuccess = false,
                        errorMessage = "Credenciais inválidas.",
                        userRegistrationData = null
                    )
                }
            } catch (e: Exception) {
                // Caso ocorra um erro durante o login
                _loginUiState.value = LoginUiState(
                    isLoading = false,
                    isSuccess = false,
                    errorMessage = "Erro ao tentar fazer login: ${e.message}",
                    userRegistrationData = null
                )
            }
        }
    }

    // Função para resetar o estado
    fun resetLoginState() {
        _loginUiState.value = LoginUiState()
    }
}