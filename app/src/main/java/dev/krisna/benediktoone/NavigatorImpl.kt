package dev.krisna.benediktoone

import dagger.hilt.android.scopes.ActivityScoped
import dev.krisna.core_navigation.NavigationCommand
import dev.krisna.core_navigation.Navigator
import javax.inject.Inject

@ActivityScoped
class NavigatorImpl @Inject constructor(
    private val holder: NavControllerHolder
) : Navigator {
    override fun navigate(command: NavigationCommand) {
        val nc = holder.navController ?: return

        // Back
        if (command is NavigationCommand.NavigateBack) {
            nc.popBackStack()
            return
        }

        // Resolve destination + options + bundle
        val destinationId = NavigationMapper.getDestinationId(command)
        val options = NavigationMapper.getNavOptions(command)
        val args = NavigationMapper.getArgs(command)

        nc.navigate(destinationId, args, options)
    }
}
