package dev.krisna.benediktoone

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import dev.krisna.core_navigation.NavigationCommand
import dev.krisna.benediktoone.R as AppR
import dev.krisna.feature_auth.R as AuthR

object NavigationMapper {
    fun getDestinationId(command: NavigationCommand): Int = when (command) {
        NavigationCommand.NavigateBack -> -1
        NavigationCommand.ToLogin     -> AuthR.id.loginFragment
        is NavigationCommand.ToRegister -> AuthR.id.registerFragment
        is NavigationCommand.ToDashboard -> AppR.id.action_global_to_main_nav_graph
        NavigationCommand.ToProductList -> AppR.id.action_dashboard_to_products
    }

    fun getNavOptions(command: NavigationCommand): NavOptions? = when (command) {
        NavigationCommand.ToLogin, is NavigationCommand.ToDashboard ->
            NavOptions.Builder().setPopUpTo(AuthR.id.auth_nav_graph, true).build()
        else -> command.navOptions
    }

    fun getArgs(command: NavigationCommand): Bundle? = when (command) {
        is NavigationCommand.ToRegister -> bundleOf("email_prefill" to command.emailPrefill)
        is NavigationCommand.ToDashboard -> bundleOf("initial_tab" to command.initialTab)
        else -> null
    }
}
