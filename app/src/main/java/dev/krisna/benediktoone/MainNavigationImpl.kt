package dev.krisna.benediktoone

import dev.krisna.core_navigation.MainNavigation
import dev.krisna.core_navigation.NavigationCommand
import dev.krisna.core_navigation.Navigator
import javax.inject.Inject

class MainNavigationImpl @Inject constructor(
    private val navigator: Navigator
) : MainNavigation {
    override fun navigateToDashboard() = navigator.navigate(NavigationCommand.ToDashboard())
}