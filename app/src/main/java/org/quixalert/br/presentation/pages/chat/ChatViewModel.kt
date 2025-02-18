package org.quixalert.br.presentation.pages.adoptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.Message
import org.quixalert.br.services.MessageService
import org.quixalert.br.utils.populateMessagesByAdoptionId
import javax.inject.Inject

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageService: MessageService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> get() = _uiState

    fun loadMessages(adoptionId: String) {
        //registerMessages("08f80aa3-2e9e-4020-b375-1dc219697cc0")
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            try {
                val messages = messageService.getMessagesByAdoptionId(adoptionId).await()
                val sortedMessages = messages.sortedBy { it.timestamp }
                _uiState.value = ChatUiState(messages = sortedMessages, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = ChatUiState(isLoading = false, errorMessage = "Error: ${e.message}")
            }
        }
    }

    fun addMessage(message: Message) {
        viewModelScope.launch {
            messageService.add(message)
            _uiState.value = _uiState.value.copy(messages = _uiState.value.messages + message)
        }
    }

    private fun registerMessages(mockedAdoptionId: String) {
        populateMessagesByAdoptionId(mockedAdoptionId).forEach { message ->
            messageService.add(message)
        }
    }
}