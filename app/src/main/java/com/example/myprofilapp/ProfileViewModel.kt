package com.example.myprofilapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun updateProfile(newName: String, newBio: String) {
        _uiState.value = _uiState.value.copy(
            name = newName,
            bio = newBio
        )
    }

    fun toggleTheme() {
        _uiState.value = _uiState.value.copy(
            isDarkMode = !_uiState.value.isDarkMode
        )
    }
}