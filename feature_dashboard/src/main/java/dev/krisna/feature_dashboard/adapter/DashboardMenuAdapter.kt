package dev.krisna.feature_dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.krisna.feature_dashboard.databinding.ItemDashboardMenuBinding
import dev.krisna.feature_dashboard.viewmodel.DashboardMenuItem

class DashboardMenuAdapter(
    private val onItemClick: (DashboardMenuItem) -> Unit
) : ListAdapter<DashboardMenuItem, DashboardMenuAdapter.MenuViewHolder>(MenuDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemDashboardMenuBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    inner class MenuViewHolder(private val binding: ItemDashboardMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DashboardMenuItem) {
            binding.ivMenuIcon.setImageResource(item.iconRes)
            binding.tvMenuTitle.text = item.title
        }
    }

    class MenuDiffCallback : DiffUtil.ItemCallback<DashboardMenuItem>() {
        override fun areItemsTheSame(oldItem: DashboardMenuItem, newItem: DashboardMenuItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DashboardMenuItem, newItem: DashboardMenuItem): Boolean {
            return oldItem == newItem
        }
    }
}