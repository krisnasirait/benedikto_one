package dev.krisna.benediktoone

import dev.krisna.core_navigation.NavigationCommand
import dev.krisna.core_navigation.Navigator
import dev.krisna.core_navigation.ProductNavigation
import javax.inject.Inject

class ProductNavigationImpl @Inject constructor(
    private val navigator: Navigator
) : ProductNavigation {
    override fun navigateToProductList() {
        navigator.navigate(NavigationCommand.ToProductList)
    }
}