package dev.krisna.benediktoone

import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dev.krisna.core_navigation.AuthNavigation

@Module
@InstallIn(ActivityRetainedComponent::class)
object NavigationModule {

    @Provides
    fun provideAuthNavigation(): AuthNavigation {
        return AuthNavigationImpl()
    }
}