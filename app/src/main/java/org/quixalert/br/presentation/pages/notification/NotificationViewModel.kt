package org.quixalert.br.presentation.pages.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.Notification
import org.quixalert.br.services.NotificationService
import org.quixalert.br.utils.populateNotifications
import javax.inject.Inject

data class NotificationUiState(
    val notifications: List<Notification> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationService: NotificationService
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> get() = _uiState

    init {
        loadNotifications()
    }

    fun loadNotifications() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val notifications = notificationService.getAll().await()
                _uiState.value = _uiState.value.copy(
                    notifications = notifications,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load notifications: ${e.message}"
                )
            }
        }
    }


    fun registerNotifications(){
        populateNotifications().forEach { notification -> notificationService.add(notification) }
    }
}