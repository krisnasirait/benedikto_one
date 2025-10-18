package dev.krisna.benediktoone

import android.util.Log
import androidx.navigation.NavController
import dev.krisna.core_navigation.AuthNavigation
import dev.krisna.core_navigation.NavigationCommand
import dev.krisna.core_navigation.Navigator
import javax.inject.Inject

class AuthNavigationImpl @Inject constructor(
    private val navigator: Navigator
) : AuthNavigation {
    override fun navigateToLogin()   = navigator.navigate(NavigationCommand.ToLogin)
    override fun navigateToRegister()= navigator.navigate(NavigationCommand.ToRegister())
}
