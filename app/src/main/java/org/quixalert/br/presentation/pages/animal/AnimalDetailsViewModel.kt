package org.quixalert.br.presentation.pages.animal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.Animal
import org.quixalert.br.services.AnimalService
import javax.inject.Inject

data class AnimalUiState(
    val animal: Animal? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val animalId: String? = null
)

@HiltViewModel
class AnimalDetailsViewModel @Inject constructor(
    private val animalService: AnimalService,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AnimalUiState(
            animalId = savedStateHandle["animalId"]
        )
    )
    val uiState: StateFlow<AnimalUiState> get() = _uiState

    fun loadPet() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            try {
                val animalId = _uiState.value.animalId
                val animal = animalId?.let { animalService.getById(it).await() }
                val errorMessage = if (animal == null) "O animal não foi encontrado" else null
                _uiState.value = AnimalUiState(animal = animal, isLoading = false, errorMessage = errorMessage)
            } catch (e: Exception) {
                _uiState.value = AnimalUiState(isLoading = false, errorMessage = "Failed to load pet: ${e.message}")
            }
        }
    }

    fun setAnimalId(animalId: String) {
        savedStateHandle["animalId"] = animalId
        _uiState.value = _uiState.value.copy(animalId = animalId)
    }
}
