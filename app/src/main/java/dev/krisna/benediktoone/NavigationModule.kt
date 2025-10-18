package dev.krisna.benediktoone

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dev.krisna.core_navigation.AuthNavigation
import dev.krisna.core_navigation.MainNavigation
import dev.krisna.core_navigation.Navigator
import dev.krisna.core_navigation.ProductNavigation

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {

    @Binds
    @ActivityScoped
    abstract fun bindAuthNavigation(impl: AuthNavigationImpl): AuthNavigation

    @Binds
    @ActivityScoped
    abstract fun bindNavigator(impl: NavigatorImpl): Navigator

    @Binds
    @ActivityScoped
    abstract fun bindMainNavigation(impl: MainNavigationImpl): MainNavigation

    @Binds
    @ActivityScoped
    abstract fun bindProductNavigation(impl: ProductNavigationImpl): ProductNavigation
}