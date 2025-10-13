package dev.krisna.feature_auth.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dev.krisna.core_ui.DialogNavigator
import dev.krisna.core_ui.DialogNavigatorImpl

@Module
@InstallIn(FragmentComponent::class)
object AuthModule {

    /**
     * Provides a [DialogNavigator] instance scoped to a Fragment.
     * Hilt injects the current Fragment, from which we can get the
     * necessary childFragmentManager.
     */
    @Provides
    fun provideDialogNavigator(fragment: Fragment): DialogNavigator {
        return DialogNavigatorImpl(fragment.childFragmentManager)
    }
}