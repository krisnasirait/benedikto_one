package dev.krisna.core_navigation

import androidx.navigation.NavOptions

sealed interface NavigationCommand {
    val navOptions: NavOptions? get() = null
    data object NavigateBack : NavigationCommand

    // Auth flow
    data object ToLogin : NavigationCommand {
        override val navOptions: NavOptions = NavOptions.Builder()
            .setPopUpTo(0, true).build() // placeholder; dimap di app
    }
    data class ToRegister(val emailPrefill: String? = null) : NavigationCommand

    // Main flow
    data class ToDashboard(val initialTab: String? = null) : NavigationCommand {
        override val navOptions: NavOptions = NavOptions.Builder()
            .setPopUpTo(0, true).build()
    }

    data object ToProductList : NavigationCommand
}