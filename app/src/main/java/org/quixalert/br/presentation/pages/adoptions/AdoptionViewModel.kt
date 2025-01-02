package org.quixalert.br.presentation.pages.adoptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.Adoption
import org.quixalert.br.domain.model.Animal
import org.quixalert.br.services.AdoptionService
import org.quixalert.br.services.AnimalService
import org.quixalert.br.utils.populateAnimalData
import javax.inject.Inject

data class AdoptionUiState(
    val animals: List<Animal> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSubmitting: Boolean = false,
    val submissionSuccess: Boolean = false
)

@HiltViewModel
class AdoptionViewModel @Inject constructor(
    private val animalService: AnimalService,
    private val adoptionService: AdoptionService
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdoptionUiState())
    val uiState: StateFlow<AdoptionUiState> get() = _uiState

    fun loadPets() {
//        registerAnimals()
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            try {
                val animals = animalService.getAll().await()
                _uiState.value = AdoptionUiState(animals = animals, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = AdoptionUiState(isLoading = false, errorMessage = "Failed to load pets: ${e.message}")
            }
        }
    }

    fun submitAdoption(adoption: Adoption) {
        _uiState.value = _uiState.value.copy(isSubmitting = true, submissionSuccess = false)
        viewModelScope.launch {
            try {
                adoptionService.add(adoption)
                _uiState.value = _uiState.value.copy(
                    isSubmitting = false,
                    submissionSuccess = true,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSubmitting = false,
                    submissionSuccess = false,
                    errorMessage = "Failed to submit adoption: ${e.message}"
                )
            }
        }
    }

    fun resetSubmissionSuccess() {
        _uiState.value = _uiState.value.copy(submissionSuccess = false)
    }

    private fun registerAnimals(){
        populateAnimalData().forEach { animal -> animalService.add(animal) }
    }
}
