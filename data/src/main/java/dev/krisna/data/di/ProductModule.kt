package dev.krisna.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.krisna.data.products.ProductRepository
import dev.krisna.data.products.ProductRepositoryImpl
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductModule {

    /**
     * Binds ProductRepositoryImpl to its ProductRepository interface.
     */
    @Binds
    @Singleton
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository
}