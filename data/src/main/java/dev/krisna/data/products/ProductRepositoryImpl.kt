package dev.krisna.data.products

import dev.krisna.data.model.Product
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient
) : ProductRepository {

    override fun getMyProducts(): Flow<List<Product>> = flow {
        try {
            val products = supabase.postgrest["products"]
                .select()
                .decodeList<Product>()
            emit(products)
        } catch (e: Exception) {
            throw IllegalStateException("Failed to fetch products from Supabase", e)
        }
    }
}