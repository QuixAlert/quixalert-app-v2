package org.quixalert.br.presentation.pages.reportsSolicitationScreen

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.Report
import org.quixalert.br.domain.model.ReportType
import org.quixalert.br.domain.model.ReportStatus
import org.quixalert.br.services.FirebaseStorageService
import org.quixalert.br.services.ReportService
import javax.inject.Inject

data class ReportsSolicitationUiState(
    val selectedType: ReportType = ReportType.AMBIENTAL,
    val description: String = "",
    val motivation: String = "",
    val title: String = "",
    val details: String = "",
    val address: String = "",
    val imageUri: Uri? = null,      // New: holds the selected image URI
    val isSubmitting: Boolean = false,
    val submissionSuccess: Boolean = false,
    val errorMessage: String? = null,
    val showSuccessDialog: Boolean = false
)

@HiltViewModel
class ReportsSolicitationViewModel @Inject constructor(
    private val reportService: ReportService,
    private val firebaseStorageService: FirebaseStorageService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportsSolicitationUiState())
    val uiState: StateFlow<ReportsSolicitationUiState> get() = _uiState

    fun updateReportType(type: ReportType) {
        _uiState.value = _uiState.value.copy(selectedType = type)
    }

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
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

    fun updateImageUri(uri: Uri?) {
        _uiState.value = _uiState.value.copy(imageUri = uri)
    }

    /**
     * Updated submitReport now accepts a [Context] so that we can upload the image (if one is selected)
     * and include its URL in the report.
     */
    fun submitReport(userId: String, context: Context) {
        // Check required fields
        if (uiState.value.description.isEmpty() ||
            uiState.value.address.isEmpty() ||
            uiState.value.motivation.isEmpty()
        ) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Por favor, preencha todos os campos obrigatórios.",
                submissionSuccess = false
            )
            return
        }

        _uiState.value = _uiState.value.copy(isSubmitting = true, submissionSuccess = false, errorMessage = null)

        val baseReport = Report(
            title = uiState.value.title,
            type = uiState.value.selectedType,
            description = uiState.value.description,
            address = uiState.value.address,
            motivation = uiState.value.motivation,
            details = uiState.value.details,
            userId = userId,
            image = "", // Initially no image URL
            status = ReportStatus.EM_ANALISE,
            answer = "" // Inicializa com resposta vazia
        )

        // If an image is selected, upload it first
        if (uiState.value.imageUri != null) {
            firebaseStorageService.uploadImageToFirebase(context, uiState.value.imageUri!!, userId) { uploadedImageUrl ->
                viewModelScope.launch {
                    try {
                        val reportToSubmit = uploadedImageUrl?.let { baseReport.copy(image = it) }
                        if (reportToSubmit != null) {
                            reportService.add(reportToSubmit)
                        }
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
        } else {
            viewModelScope.launch {
                try {
                    reportService.add(baseReport)
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