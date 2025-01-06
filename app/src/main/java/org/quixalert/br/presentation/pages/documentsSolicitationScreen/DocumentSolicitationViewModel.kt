package org.quixalert.br.presentation.pages.documentsSolicitationScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.Document
import org.quixalert.br.domain.model.DocumentType
import org.quixalert.br.services.DocumentService
import javax.inject.Inject

data class DocumentsSolicitationUiState(
    val selectedTypeDocument: DocumentType = DocumentType.ALVARA,
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
class DocumentsSolicitationViewModel @Inject constructor(
    private val documentService: DocumentService
) : ViewModel() {

    private val _uiState = MutableStateFlow(DocumentsSolicitationUiState())
    val uiState: StateFlow<DocumentsSolicitationUiState> get() = _uiState

    fun updateDocumentType(type: DocumentType) {
        _uiState.value = _uiState.value.copy(selectedTypeDocument = type)
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

    fun submitDocument() {
        val document = Document(
            documentType = uiState.value.selectedTypeDocument,
            descriptions = uiState.value.description,
            address = uiState.value.address,
            reason = uiState.value.motivation,
            extraDetails = uiState.value.details
        )

        if (document.descriptions.isEmpty() || document.address.isEmpty() || document.reason.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Por favor, preencha todos os campos obrigatórios.",
                submissionSuccess = false
            )
            return
        }

        _uiState.value = _uiState.value.copy(isSubmitting = true, submissionSuccess = false, errorMessage = null)

        viewModelScope.launch {
            try {
                documentService.add(document)
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
                    errorMessage = "Failed to submit document: ${e.message}"
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