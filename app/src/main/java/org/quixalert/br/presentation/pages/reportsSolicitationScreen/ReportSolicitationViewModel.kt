package org.quixalert.br.presentation.pages.reportsSolicitationScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.Report
import org.quixalert.br.domain.model.ReportType
import org.quixalert.br.services.ReportService
import javax.inject.Inject

data class ReportsSolicitationUiState(
    val selectedType: ReportType = ReportType.AMBIENTAL,
    val description: String = "",
    val motivation: String = "",
    val details: String = "",
    val address: String = "",
    val isSubmitting: Boolean = false,
    val submissionSuccess: Boolean = false,
    val errorMessage: String? = null,
    val showSuccessDialog: Boolean = false
)

@HiltViewModel
class ReportsSolicitationViewModel @Inject constructor(
    private val reportService: ReportService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportsSolicitationUiState())
    val uiState: StateFlow<ReportsSolicitationUiState> get() = _uiState

    fun updateReportType(type: ReportType) {
        _uiState.value = _uiState.value.copy(selectedType = type)
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun updateMotivation(motivation: String) {
        _uiState.value = _uiState.value.copy(motivation = motivation)
    }

    fun updateDetails(details: String) {
        _uiState.value = _uiState.value.copy(details = details)
    }

    fun updateAddress(address: String) {
        _uiState.value = _uiState.value.copy(address = address)
    }

    fun submitReport() {
        val report = Report(
            type = uiState.value.selectedType,
            description = uiState.value.description,
            address = uiState.value.address,
            motivation = uiState.value.motivation,
            details = uiState.value.details
        )

        if (report.description.isEmpty() || report.address.isEmpty() || report.motivation.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Por favor, preencha todos os campos obrigatórios.",
                submissionSuccess = false
            )
            return
        }

        _uiState.value = _uiState.value.copy(isSubmitting = true, submissionSuccess = false, errorMessage = null)

        viewModelScope.launch {
            try {
                reportService.add(report)
                _uiState.value = _uiState.value.copy(
                    isSubmitting = false,
                    submissionSuccess = true,
                    errorMessage = null,
                    showSuccessDialog = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSubmitting = false,
                    submissionSuccess = false,
                    errorMessage = "Falha ao enviar a denúncia: ${e.message}"
                )
            }
        }
    }

    fun resetSubmissionState() {
        _uiState.value = _uiState.value.copy(
            submissionSuccess = false,
            errorMessage = null,
            isSubmitting = false,
            showSuccessDialog = false
        )
    }
}