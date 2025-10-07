package dev.krisna.feature_auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.krisna.data.AuthRepository
import io.github.jan.supabase.auth.exception.AuthRestException
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

            // 2. Panggil repository
            authRepository.signUpEmail(email, password)
                .onSuccess {
                    _registerUiState.value = RegisterUiState.Idle
                }
                .onFailure { exception ->
                    val errorMessage = if (exception is AuthRestException) {
                        exception.description ?: "Invalid email or password"
                    } else {
                        exception.message ?: "An unknown error occurred"
                    }
                    _registerUiState.value = RegisterUiState.Error(errorMessage)
                }
        }
    }

    fun resetUiState() {
        _registerUiState.value = RegisterUiState.Idle
    }
}