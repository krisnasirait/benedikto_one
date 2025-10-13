package dev.krisna.feature_auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.krisna.core_navigation.AuthNavigation
import dev.krisna.core_ui.ConfirmationDialogFragment
import dev.krisna.core_ui.DialogNavigator
import dev.krisna.core_ui.ErrorDialogFragment
import dev.krisna.feature_auth.R
import dev.krisna.feature_auth.databinding.FragmentRegisterBinding
import dev.krisna.feature_auth.viewmodel.RegisterUiState
import dev.krisna.feature_auth.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    @Inject
    lateinit var authNavigation: AuthNavigation

    @Inject
    lateinit var dialogNavigator: DialogNavigator

    // Define a unique request key for our success dialog
    private val registrationSuccessKey = "registration_success_dialog"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDialogListeners()
        observeRegisterUiState()

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            viewModel.register(email, password)
        }

        binding.tvLogin.setOnClickListener {
            authNavigation.navigateToLogin()
        }
    }

    private fun observeRegisterUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerUiState.collect { state ->
                    when (state) {
                        is RegisterUiState.Error -> {
                            setLoading(false)
                            dialogNavigator.showError(
                                message = state.message ?: "An unknown error occurred"
                            )
                        }
                        RegisterUiState.Idle -> {
                            setLoading(false)
                        }
                        RegisterUiState.Loading -> {
                            setLoading(true)
                        }
                        // --- HANDLE THE NEW SUCCESS STATE ---
                        RegisterUiState.SuccessAwaitingVerification -> {
                            setLoading(false)
                            // Use a confirmation dialog to give the user instructions
                            dialogNavigator.showConfirmation(
                                title = "Registration Successful",
                                message = "A verification link has been sent to your email. Please verify your account before logging in.",
                                positiveText = "OK",
                                negativeText = null, // Set to null to hide the negative button
                                requestKey = registrationSuccessKey
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setupDialogListeners() {
        // Listener for the error dialog
        childFragmentManager.setFragmentResultListener(
            ErrorDialogFragment.DEFAULT_REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            if (bundle.getBoolean(ErrorDialogFragment.RESULT_ACKNOWLEDGED)) {
                viewModel.resetUiState()
            }
        }

        // --- ADD A NEW LISTENER FOR THE SUCCESS DIALOG ---
        // This will be triggered when the user clicks "OK" on the success dialog.
        childFragmentManager.setFragmentResultListener(
            registrationSuccessKey,
            viewLifecycleOwner
        ) { _, bundle ->
            // Check if the positive button was clicked (it's the only one)
            if (bundle.getBoolean(ConfirmationDialogFragment.RESULT_CONFIRMED)) {
                // Reset the state and navigate to the login screen
                viewModel.resetUiState()
                authNavigation.navigateToLogin()
            }
        }
    }

    // The observeSessionStatus is no longer needed to handle the immediate sign-up success,
    // as our new UI state does it more explicitly.
    // You can remove it or keep it if you need it for other session-related logic.

    private fun setLoading(isLoading: Boolean) {
        val originalText = getString(R.string.title_register)

        if (isLoading) {
            binding.cipLoading.show()
            binding.btnRegister.text = ""
            binding.btnRegister.isEnabled = false
            binding.etEmail.isEnabled = false
            binding.etPassword.isEnabled = false
            binding.tvLogin.isEnabled = false
        } else {
            binding.cipLoading.hide()
            binding.btnRegister.text = originalText
            binding.btnRegister.isEnabled = true
            binding.etEmail.isEnabled = true
            binding.etPassword.isEnabled = true
            binding.tvLogin.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}