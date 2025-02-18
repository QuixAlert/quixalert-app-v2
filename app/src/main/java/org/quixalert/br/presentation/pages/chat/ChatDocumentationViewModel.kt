package org.quixalert.br.presentation.pages.adoptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.MessageDocumentation
import org.quixalert.br.services.MessageDocumentationService
import org.quixalert.br.utils.populateMessagesByDocumentId
import javax.inject.Inject

data class ChatUiStateDocumentation(
    val messages: List<MessageDocumentation> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class ChatDocumentationViewModel @Inject constructor(
    private val messageService: MessageDocumentationService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiStateDocumentation())
    val uiState: StateFlow<ChatUiStateDocumentation> get() = _uiState

    fun loadMessages(adoptionId: String) {
//        registerMessages("7d5c33b3-5c91-45ad-8f7b-3eac66e3e523")
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            try {
                val messages = messageService.getMessagesByDocumentId(adoptionId).await()
                val sortedMessages = messages.sortedBy { it.timestamp }
                _uiState.value = ChatUiStateDocumentation(messages = sortedMessages, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = ChatUiStateDocumentation(isLoading = false, errorMessage = "Error: ${e.message}")
            }
        }
    }

    fun addMessage(message: MessageDocumentation) {
        viewModelScope.launch {
            messageService.add(message)
            _uiState.value = _uiState.value.copy(messages = _uiState.value.messages + message)
        }
    }

    private fun registerMessages(mockedDocumentId: String) {
        populateMessagesByDocumentId(mockedDocumentId).forEach { message ->
            messageService.add(message)
        }
    }
}