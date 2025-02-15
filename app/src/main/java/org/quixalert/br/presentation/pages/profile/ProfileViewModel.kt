package org.quixalert.br.presentation.pages.profile

import com.google.firebase.firestore.FirebaseFirestore
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import org.quixalert.br.services.ReportService
import org.quixalert.br.services.FirebaseStorageService
import org.quixalert.br.services.FirebaseAuthService
import javax.inject.Inject

data class ProfileUiState(
    val adoptionsByUser: List<AdoptionT> = emptyList(),
    val documentsSolicitationByUser: List<Document> = emptyList(),
    val reportsSolicitationByUser: List<Report> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
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
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            try {
                // Filter by user id if necessary
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

                _uiState.value = ProfileUiState(
                    adoptionsByUser = adoptions,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Failed to load pets: ${e.message}")
            }
        }
    }

    fun updateProfileImage(context: Context, imageUri: Uri, userId: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                firebaseStorageService.uploadImageToFirebase(context, imageUri, userId) { uploadedImageUrl ->
                    if (uploadedImageUrl != null) {
                        // Update Firestore
                        val firestore = FirebaseFirestore.getInstance()
                        firestore.collection("users")
                            .document(userId)
                            .update("profileImage", uploadedImageUrl)
                            .addOnSuccessListener {
                                _uiState.value = _uiState.value.copy(isLoading = false)
                                onComplete(true)
                            }
                            .addOnFailureListener {
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    errorMessage = "Failed to update profile image"
                                )
                                onComplete(false)
                            }
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "Failed to upload image"
                        )
                        onComplete(false)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error: ${e.message}"
                )
                onComplete(false)
            }
        }
    }
}