package dev.krisna.benediktoone

import androidx.navigation.NavController
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class NavControllerHolder @Inject constructor() {
    var navController: NavController? = null
}