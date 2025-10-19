package dev.krisna.benediktoone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.krisna.data.auth.AuthRepository
import dev.krisna.data.repository.UserPreferencesRepository
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SplashDestination {
    object Uninitialized : SplashDestination
    object ToDashboard : SplashDestination
    object ToLogin : SplashDestination
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _destination = MutableStateFlow<SplashDestination>(SplashDestination.Uninitialized)
    val destination = _destination.asStateFlow()

    init {
        checkUserStatus()
    }

    private fun checkUserStatus() {
        viewModelScope.launch {
            val shouldRememberUser = userPreferencesRepository.rememberMeFlow.first()
            val sessionStatus = authRepository.observeSessionStatus().first()

            if (sessionStatus is SessionStatus.Authenticated && shouldRememberUser) {
                _destination.value = SplashDestination.ToDashboard
            } else {
                userPreferencesRepository.setRememberMe(false)
                _destination.value = SplashDestination.ToLogin
            }
        }
    }
}