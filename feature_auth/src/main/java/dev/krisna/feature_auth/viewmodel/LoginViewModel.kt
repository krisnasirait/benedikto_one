package dev.krisna.feature_auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.krisna.data.auth.AuthRepository
import dev.krisna.data.repository.UserPreferencesRepository
import io.github.jan.supabase.auth.status.SessionStatus
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    data class Error(val message: String?) : LoginUiState
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val sessionState = authRepository.observeSessionStatus()
        .stateIn(viewModelScope, SharingStarted.Eagerly, SessionStatus.Initializing)

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    // ✅ 3. Ubah parameter fungsi login
    fun login(email: String, password: String, shouldRemember: Boolean) {
        viewModelScope.launch {
            _loginUiState.value = LoginUiState.Loading

            authRepository.signInEmail(email, password)
                .onSuccess {
                    userPreferencesRepository.setRememberMe(shouldRemember)
                    _loginUiState.value = LoginUiState.Idle
                }
                .onFailure { exception ->
                    val errorMessage = exception.message ?: "An unknown error occurred"
                    _loginUiState.value = LoginUiState.Error(errorMessage)
                }
        }
    }

    fun resetUiState() {
        _loginUiState.value = LoginUiState.Idle
    }
}