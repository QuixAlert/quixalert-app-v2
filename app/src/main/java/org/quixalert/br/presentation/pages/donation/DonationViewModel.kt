package org.quixalert.br.presentation.pages.donation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.Donation
import org.quixalert.br.services.DonationService
import javax.inject.Inject

data class DonationUiState(
    val donationAmount: String = "",
    val isSubmitting: Boolean = false,
    val submissionSuccess: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class DonationViewModel @Inject constructor(
    private val donationService: DonationService
) : ViewModel() {

    private val _uiState = MutableStateFlow(DonationUiState())
    val uiState: StateFlow<DonationUiState> get() = _uiState

    fun updateDonationAmount(amount: String) {
        _uiState.value = _uiState.value.copy(donationAmount = amount)
    }

    fun submitDonation() {
        val amount = uiState.value.donationAmount.toDoubleOrNull()

        if (amount == null || amount <= 0) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Por favor, insira um valor válido para a doação.",
                submissionSuccess = false
            )
            return
        }

        _uiState.value = _uiState.value.copy(isSubmitting = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val donation = Donation(amount = amount)
                donationService.add(donation)
                _uiState.value = _uiState.value.copy(
                    isSubmitting = false,
                    submissionSuccess = true,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSubmitting = false,
                    errorMessage = "Erro ao realizar a doação: ${e.message}",
                    submissionSuccess = false
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = _uiState.value.copy(
            submissionSuccess = false,
            errorMessage = null,
            isSubmitting = false,
            donationAmount = ""
        )
    }
}
