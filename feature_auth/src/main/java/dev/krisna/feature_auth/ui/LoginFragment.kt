package dev.krisna.feature_auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.krisna.core_navigation.AuthNavigation
import dev.krisna.core_navigation.MainNavigation
import dev.krisna.core_ui.DialogNavigator
import dev.krisna.core_ui.ErrorDialogFragment
import dev.krisna.feature_auth.R
import dev.krisna.feature_auth.databinding.FragmentLoginBinding
import dev.krisna.feature_auth.viewmodel.LoginUiState
import dev.krisna.feature_auth.viewmodel.LoginViewModel
import io.github.jan.supabase.auth.status.SessionSource
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var authNavigation: AuthNavigation

    @Inject
    lateinit var mainNavigation: MainNavigation

    @Inject
    lateinit var dialogNavigator: DialogNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDialogListener()
        observeSessionStatus()
        observeLoginUiState()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            val shouldRemember = binding.cbRememberMe.isChecked

            viewModel.login(email, password, shouldRemember)
        }
        binding.registerTextView.setOnClickListener {
            authNavigation.navigateToRegister()
        }
    }

    private fun observeLoginUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginUiState.collect { state ->
                    when (state) {
                        is LoginUiState.Error -> {
                            setLoading(false)
                            // Show the error dialog instead of a Toast
                            dialogNavigator.showError(
                                message = state.message ?: "An unknown error occurred"
                            )
                        }
                        LoginUiState.Idle -> {
                            setLoading(false)
                        }
                        LoginUiState.Loading -> {
                            setLoading(true)
                        }
                    }
                }
            }
        }
    }

    private fun setupDialogListener() {
        // Listen for when the error dialog is dismissed.
        // This is the correct place to reset the ViewModel's state.
        childFragmentManager.setFragmentResultListener(
            ErrorDialogFragment.DEFAULT_REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val acknowledged = bundle.getBoolean(ErrorDialogFragment.RESULT_ACKNOWLEDGED)
            if (acknowledged) {
                viewModel.resetUiState()
            }
        }
    }

    private fun observeSessionStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sessionState.collect { status ->
                    if (status is SessionStatus.Authenticated && status.source is SessionSource.SignIn) {
                        setLoading(false)
                        mainNavigation.navigateToDashboard()
                    }
                }
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        // Store the original button text if you need to support multiple languages
        val originalText = getString(R.string.title_login) // Assuming you have this in strings.xml

        if (isLoading) {
            binding.cipLoading.show()
            // Make the button text blank to show the indicator clearly
            binding.btnLogin.text = ""
            binding.btnLogin.isEnabled = false
            binding.etEmail.isEnabled = false
            binding.etPassword.isEnabled = false
            binding.registerTextView.isEnabled = false
        } else {
            binding.cipLoading.hide()
            // Restore the button text
            binding.btnLogin.text = originalText
            binding.btnLogin.isEnabled = true
            binding.etEmail.isEnabled = true
            binding.etPassword.isEnabled = true
            binding.registerTextView.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}