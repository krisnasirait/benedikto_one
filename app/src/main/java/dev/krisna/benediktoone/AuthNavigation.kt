package dev.krisna.benediktoone

import androidx.navigation.NavController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityScoped
import dev.krisna.core_navigation.AuthNavigation

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationBindingModule {

    @Binds
    @ActivityScoped
    abstract fun bindAuthNavigation(impl: AuthNavigationImpl): AuthNavigation
}