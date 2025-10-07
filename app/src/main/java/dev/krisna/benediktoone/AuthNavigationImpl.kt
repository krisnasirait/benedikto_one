package dev.krisna.benediktoone

import android.util.Log
import androidx.navigation.NavController
import dev.krisna.core_navigation.AuthNavigation
import javax.inject.Inject

class AuthNavigationImpl @Inject constructor(
    private val holder: NavControllerHolder
) : AuthNavigation {

    override fun navigateToLogin() {
        val nc = holder.navController
        nc?.navigate(dev.krisna.feature_auth.R.id.loginFragment) // pakai action / global action jika ada
    }

    override fun navigateToRegister() {
        val nc = holder.navController
        nc?.navigate(dev.krisna.feature_auth.R.id.registerFragment)
    }
}