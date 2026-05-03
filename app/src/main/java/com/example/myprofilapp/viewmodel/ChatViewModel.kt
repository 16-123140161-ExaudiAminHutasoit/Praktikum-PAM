package com.example.myprofilapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofilapp.model.ChatMessage
import com.example.myprofilapp.repository.AIRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.example.myprofilapp.database.NoteEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val lastMessage: String? = null,
    val analysisResult: String? = null
)

class ChatViewModel(private val aiRepository: AIRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun analyzeAllNotes(notes: List<NoteEntity>) {
        if (notes.isEmpty()) {
            _uiState.update { it.copy(error = "Tidak ada catatan untuk dianalisis.") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null, analysisResult = null) }

        viewModelScope.launch {
            val notesJson = Json.encodeToString(notes.map { mapOf("judul" to it.title, "isi" to it.content) })
            aiRepository.analyzeNotes(notesJson)
                .onSuccess { result ->
                    _uiState.update { it.copy(analysisResult = result, isLoading = false) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(error = error.message, isLoading = false) }
                }
        }
    }

    fun sendMessage(message: String) {
        if (message.isBlank()) return

        // Add user message to UI
        _uiState.update { it.copy(
            messages = it.messages + ChatMessage(message, isUser = true),
            isLoading = true,
            error = null,
            lastMessage = message
        ) }

        viewModelScope.launch {
            executeChat(message)
        }
    }

    fun retryLastMessage() {
        val lastMsg = _uiState.value.lastMessage ?: return
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            executeChat(lastMsg)
        }
    }

    private suspend fun executeChat(message: String) {
        aiRepository.chat(message)
            .onSuccess { response ->
                _uiState.update { it.copy(
                    messages = it.messages + ChatMessage(response, isUser = false),
                    isLoading = false
                ) }
            }
            .onFailure { error ->
                _uiState.update { it.copy(
                    error = error.message ?: "Unknown error occurred. Please check your internet connection.",
                    isLoading = false
                ) }
            }
    }

    fun clearChat() {
        aiRepository.clearChat()
        _uiState.update { ChatUiState() }
    }
}
