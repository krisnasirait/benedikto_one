package dev.krisna.feature_products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.krisna.data.products.ProductRepository
import dev.krisna.feature_products.ProductsUiState
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ProductsViewModel @Inject constructor(
    productRepository: ProductRepository
) : ViewModel() {

    val uiState: StateFlow<ProductsUiState> = productRepository.getMyProducts()
        .map { products ->
            if (products.isEmpty()) {
                ProductsUiState.Empty
            } else {
                ProductsUiState.Success(products)
            }
        }
        .catch { exception ->
            emit(ProductsUiState.Error(exception.message ?: "An unknown error occurred"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ProductsUiState.Loading
        )
}