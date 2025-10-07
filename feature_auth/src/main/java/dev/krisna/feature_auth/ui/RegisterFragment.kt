package dev.krisna.feature_auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.krisna.core_navigation.AuthNavigation
import dev.krisna.feature_auth.R
import dev.krisna.feature_auth.databinding.FragmentLoginBinding
import dev.krisna.feature_auth.databinding.FragmentRegisterBinding
import dev.krisna.feature_auth.viewmodel.LoginUiState
import dev.krisna.feature_auth.viewmodel.LoginViewModel
import dev.krisna.feature_auth.viewmodel.RegisterUiState
import dev.krisna.feature_auth.viewmodel.RegisterViewModel
import io.github.jan.supabase.auth.status.SessionSource
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    @Inject
    lateinit var authNavigation: AuthNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSessionStatus()
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
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                            viewModel.resetUiState()
                        }
                        RegisterUiState.Idle -> {
                            setLoading(false)
                        }
                        RegisterUiState.Loading -> {
                            setLoading(true)
                        }
                    }
                }
            }
        }
    }

    private fun observeSessionStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sessionState.collect { status ->
                    if (status is SessionStatus.Authenticated && status.source is SessionSource.SignIn) {
                        setLoading(false)
                        Toast.makeText(requireContext(), "Login Success!", Toast.LENGTH_SHORT).show()
                        // navigate to home
                        // findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            }
        }
    }

    /**
     * Menyesuaikan fungsi ini untuk mengontrol CircularProgressIndicator.
     */
    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            // Menggunakan .show() untuk menampilkan indikator dengan animasi
            binding.cipLoading.show()
            binding.btnRegister.isEnabled = false
            binding.etEmail.isEnabled = false
            binding.etPassword.isEnabled = false
        } else {
            // Menggunakan .hide() untuk menyembunyikan indikator dengan animasi
            binding.cipLoading.hide()
            binding.btnRegister.isEnabled = true
            binding.etEmail.isEnabled = true
            binding.etPassword.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}