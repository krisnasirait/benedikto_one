package dev.krisna.feature_dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.krisna.feature_dashboard.R

// Data class untuk merepresentasikan setiap item menu
data class DashboardMenuItem(
    val id: Int,
    val title: String,
    val iconRes: Int // Resource ID untuk icon
)
class DashboardViewModel : ViewModel() {

    private val _menuItems = MutableLiveData<List<DashboardMenuItem>>()
    val menuItems: LiveData<List<DashboardMenuItem>> = _menuItems

    init {
        loadMenuItems()
    }

    private fun loadMenuItems() {
        val menus = listOf(
            DashboardMenuItem(1, "Clients", R.drawable.ic_clients),
            DashboardMenuItem(2, "Products", R.drawable.ic_products),
            DashboardMenuItem(3, "Orders", R.drawable.ic_orders),
        )
        _menuItems.value = menus
    }
}