package dev.krisna.benediktoone.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.krisna.benediktoone.R
import dev.krisna.benediktoone.viewmodel.SplashDestination
import dev.krisna.benediktoone.viewmodel.SplashViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.destination.collect { destination ->
                    when (destination) {
                        SplashDestination.ToDashboard -> {
                            findNavController().navigate(R.id.action_global_to_main_nav_graph)
                        }
                        SplashDestination.ToLogin -> {
                            findNavController().navigate(R.id.action_splashFragment_to_auth_nav_graph)
                        }
                        SplashDestination.Uninitialized -> {  }
                    }
                }
            }
        }
    }
}