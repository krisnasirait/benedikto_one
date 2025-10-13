package dev.krisna.feature_auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.krisna.data.AuthRepository
import io.github.jan.supabase.auth.status.SessionStatus
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


sealed interface RegisterUiState {
    object Idle : RegisterUiState
    object Loading : RegisterUiState
    // ADD THIS NEW STATE
    object SuccessAwaitingVerification : RegisterUiState
    data class Error(val message: String?) : RegisterUiState
}


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val sessionState = authRepository.observeSessionStatus()
        .stateIn(viewModelScope, SharingStarted.Eagerly, SessionStatus.Initializing)

    private val _registerUiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val registerUiState: StateFlow<RegisterUiState> = _registerUiState.asStateFlow()

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registerUiState.value = RegisterUiState.Loading

            authRepository.signUpEmail(email, password)
                .onSuccess {
                    // --- FIX IS HERE ---
                    // Instead of going to Idle, emit the new success state.
                    _registerUiState.value = RegisterUiState.SuccessAwaitingVerification
                }
                .onFailure { exception ->
                    val errorMessage = exception.message ?: "An unknown error occurred"
                    _registerUiState.value = RegisterUiState.Error(errorMessage)
                }
        }
    }

    fun resetUiState() {
        _registerUiState.value = RegisterUiState.Idle
    }
}