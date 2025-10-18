package dev.krisna.data.products

import dev.krisna.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getMyProducts(): Flow<List<Product>>
}