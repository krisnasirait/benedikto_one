package dev.krisna.benediktoone

import androidx.navigation.NavController
import dev.krisna.core_navigation.AuthNavigation
import jakarta.inject.Inject
import dev.krisna.feature_auth.R as AuthR

class AuthNavigationImpl @Inject constructor() : AuthNavigation {

    private var navController: NavController? = null

    fun bind(navController: NavController) {
        this.navController = navController
    }

    override fun navigateToLogin() {
        navController?.navigate(dev.krisna.feature_auth.R.id.loginFragment)
    }
}
