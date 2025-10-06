package dev.krisna.feature_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.krisna.feature_auth.databinding.FragmentLoginBinding
import io.github.jan.supabase.auth.status.SessionSource
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sessionState.collect { status ->
                when (status) {
                    is SessionStatus.Authenticated -> {
                        when (status.source) {
                            is SessionSource.SignIn -> {
                                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                                // navigate to home
                            }
                            else -> Unit
                        }
                    }
                    is SessionStatus.NotAuthenticated -> {
                        if (status.isSignOut) {
                            Toast.makeText(requireContext(), "Signed out", Toast.LENGTH_SHORT).show()
                        }
                    }
                    SessionStatus.Initializing -> {
                        // show loading
                    }
                    is SessionStatus.RefreshFailure -> {
                        Toast.makeText(requireContext(), "Token expired", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
