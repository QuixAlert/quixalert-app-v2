package org.quixalert.br.presentation.pages.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.AdoptionT
import org.quixalert.br.domain.model.Document
import org.quixalert.br.domain.model.Report
import org.quixalert.br.services.AdoptionService
import org.quixalert.br.services.AnimalService
import org.quixalert.br.services.DocumentService
import org.quixalert.br.services.FirebaseAuthService
import org.quixalert.br.services.FirebaseStorageService
import org.quixalert.br.services.ReportService
import javax.inject.Inject

data class ProfileUiState(
    val adoptionsByUser: List<AdoptionT> = emptyList(),
    val documentsSolicitationByUser: List<Document> = emptyList(),
    val reportsSolicitationByUser: List<Report> = emptyList(),
    val isLoadingAdoptions: Boolean = false,
    val isLoadingDocuments: Boolean = false,
    val isLoadingReports: Boolean = false,
    val errorAdoptions: String? = null,
    val errorDocuments: String? = null,
    val errorReports: String? = null,
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val adoptionService: AdoptionService,
    private val documentService: DocumentService,
    private val reportService: ReportService,
    private val animalService: AnimalService,
    private val firebaseStorageService: FirebaseStorageService,
    private val firebaseAuthService: FirebaseAuthService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> get() = _uiState

    fun loadAdoptionsByUserId(userId: String) {
        _uiState.value = _uiState.value.copy(isLoadingAdoptions = true, errorAdoptions = null)
        viewModelScope.launch {
            try {
                val adoptions = adoptionService.getAdoptionsByUserId(userId).await().map { adoptionT ->
                    AdoptionT(
                        id = adoptionT.id,
                        animal = animalService.getById(adoptionT.animalId).await(),
                        status = adoptionT.status,
                        address = adoptionT.address,
                        livingDescription = adoptionT.livingDescription,
                        otherAnimals = adoptionT.otherAnimals,
                        monthlyIncome = adoptionT.monthlyIncome,
                        householdDescription = adoptionT.householdDescription,
                        adoptionReason = adoptionT.adoptionReason,
                        visitDate = adoptionT.visitDate,
                        userId = adoptionT.userId,
                        animalId = adoptionT.animalId
                    )
                }
                _uiState.value = _uiState.value.copy(
                    adoptionsByUser = adoptions,
                    isLoadingAdoptions = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingAdoptions = false,
                    errorAdoptions = "Failed to load adoptions: ${e.message}"
                )
            }
        }
    }

    fun loadDocumentsByUserId(userId: String) {
        _uiState.value = _uiState.value.copy(isLoadingDocuments = true, errorDocuments = null)
        viewModelScope.launch {
            try {
                val documents = documentService.getDocumentByUserId(userId).await().map { document ->
                    Document(
                        id = document.id,
                        documentType = document.documentType,
                        status = document.status,
                        descriptions = document.descriptions,
                        address = document.address,
                        reason = document.reason,
                        extraDetails = document.extraDetails,
                        userId = document.userId
                    )
                }
                _uiState.value = _uiState.value.copy(
                    documentsSolicitationByUser = documents,
                    isLoadingDocuments = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingDocuments = false,
                    errorDocuments = "Failed to load documents: ${e.message}"
                )
            }
        }
    }

    fun loadReportByUserId(userId: String) {
        _uiState.value = _uiState.value.copy(isLoadingReports = true, errorReports = null)
        viewModelScope.launch {
            try {
                val reports = reportService.getReportByUserId(userId).await()
                val sortedReports = reports.sortedByDescending { it.date }
                
                _uiState.value = _uiState.value.copy(
                    reportsSolicitationByUser = sortedReports,
                    isLoadingReports = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingReports = false,
                    errorReports = "Erro ao carregar denúncias: ${e.message}"
                )
            }
        }
    }

    fun updateProfileImage(context: Context, imageUri: Uri, userId: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                firebaseStorageService.uploadImageToFirebase(context, imageUri, userId) { uploadedImageUrl ->
                    if (uploadedImageUrl != null) {
                        val firestore = FirebaseFirestore.getInstance()
                        firestore.collection("users")
                            .document(userId)
                            .update("profileImage", uploadedImageUrl)
                            .addOnSuccessListener {
                                onComplete(true)
                            }
                            .addOnFailureListener {
                                _uiState.value = _uiState.value.copy(
                                    errorAdoptions = "Failed to update profile image"
                                )
                                onComplete(false)
                            }
                    } else {
                        _uiState.value = _uiState.value.copy(
                            errorAdoptions = "Failed to upload image"
                        )
                        onComplete(false)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorAdoptions = "Error: ${e.message}"
                )
                onComplete(false)
            }
        }
    }
}