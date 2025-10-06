package dev.krisna.feature_auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.krisna.data.AuthRepository
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.status.SessionStatus
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val sessionState = authRepository.observeSessionStatus()
        .stateIn(viewModelScope, SharingStarted.Eagerly, SessionStatus.Initializing)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signIn(email, password)
        }
    }
}
