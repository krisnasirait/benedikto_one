package dev.krisna.feature_products

import dev.krisna.data.model.Product

sealed interface ProductsUiState {
    object Loading : ProductsUiState
    data class Success(val products: List<Product>) : ProductsUiState
    data class Error(val message: String) : ProductsUiState
    object Empty : ProductsUiState
}