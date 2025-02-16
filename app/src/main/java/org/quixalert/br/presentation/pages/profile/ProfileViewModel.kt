package org.quixalert.br.presentation.pages.profile

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
import javax.inject.Inject
import java.time.format.DateTimeFormatter

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
    private val animalService: AnimalService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> get() = _uiState

    fun loadAdoptions() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            try {
                // Filter by user id if necessary
                val adoptions = adoptionService.getAll().await().map { adoptionT ->
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
}