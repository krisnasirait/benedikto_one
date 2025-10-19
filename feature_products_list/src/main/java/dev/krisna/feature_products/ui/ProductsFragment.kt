// In feature_products/src/main/java/dev/krisna/feature_products/ui/ProductsFragment.kt
package dev.krisna.feature_products.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.krisna.feature_products.ui.ProductsUiState
import dev.krisna.feature_products.R
import dev.krisna.feature_products.adapter.ProductListAdapter
import dev.krisna.feature_products.databinding.FragmentProductsBinding
import dev.krisna.feature_products.viewmodel.ProductsViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products) {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductsViewModel by viewModels()
    private val productAdapter = ProductListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductsBinding.bind(view)

        binding.rvProducts.adapter = productAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    handleUiState(state)
                }
            }
        }
    }

    private fun handleUiState(state: ProductsUiState) {
        binding.progressBar.isVisible = state is ProductsUiState.Loading
        binding.rvProducts.isVisible = state is ProductsUiState.Success
        binding.tvInfo.isVisible = state is ProductsUiState.Empty || state is ProductsUiState.Error

        when (state) {
            is ProductsUiState.Success -> {
                productAdapter.submitList(state.products)
            }
            is ProductsUiState.Empty -> {
                binding.tvInfo.text = "Anda belum memiliki produk."
            }
            is ProductsUiState.Error -> {
                binding.tvInfo.text = state.message
            }
            is ProductsUiState.Loading -> {
                // Handled by progress bar visibility
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hindari memory leak dengan membersihkan referensi binding
        binding.rvProducts.adapter = null
        _binding = null
    }
}