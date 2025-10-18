package dev.krisna.feature_products.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.krisna.data.model.Product
import dev.krisna.feature_products.databinding.ItemProductBinding
import java.text.NumberFormat
import java.util.Locale

class ProductListAdapter : ListAdapter<Product, ProductListAdapter.ProductViewHolder>(ProductDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.tvProductName.text = product.name

            // Tampilkan SKU jika ada, sembunyikan jika tidak ada
            if (product.sku.isNullOrBlank()) {
                binding.tvProductSku.visibility = View.GONE
            } else {
                binding.tvProductSku.visibility = View.VISIBLE
                binding.tvProductSku.text = "SKU: ${product.sku}"
            }

            // Tampilkan harga jika ada, atau tampilkan pesan lain
            binding.tvProductPrice.text = product.price?.let { formatToRupiah(it) } ?: "Harga tidak tersedia"
        }

        private fun formatToRupiah(price: Double): String {
            val localeID = Locale("in", "ID")
            val numberFormat = NumberFormat.getCurrencyInstance(localeID)
            numberFormat.maximumFractionDigits = 0 // Tidak menampilkan desimal
            return numberFormat.format(price)
        }
    }

    object ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem == newItem
    }
}