package org.quixalert.br.presentation.pages.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quixalert.br.services.ReportService
import javax.inject.Inject

data class ReportUiState(
    val report: ReportDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val rating: Int = 0,
    val comment: String = ""
)

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportService: ReportService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState

    fun loadReport(reportId: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        
        viewModelScope.launch {
            try {
                val report = reportService.getById(reportId).await()
                if (report != null) {
                    _uiState.value = _uiState.value.copy(
                        report = ReportDetail(
                            id = report.id,
                            title = report.title,
                            category = report.type.name,
                            description = report.description,
                            responsible = "Prefeitura de Quixadá",
                            responsibleIcon = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR3Ze3JUrTPRe5LIttbKF8ouyH-0wbUnrbjBQ&s",
                            status = "Em análise",
                            answer = report.details,
                            gallery = listOf(report.image)
                        ),
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = "Relatório não encontrado",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Erro ao carregar relatório: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun updateRating(newRating: Int) {
        _uiState.value = _uiState.value.copy(rating = newRating)
    }

    fun updateComment(newComment: String) {
        _uiState.value = _uiState.value.copy(comment = newComment)
    }
}
