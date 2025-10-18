package dev.krisna.feature_dashboard.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.R
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.krisna.core_navigation.ProductNavigation
import dev.krisna.feature_dashboard.adapter.DashboardMenuAdapter
import dev.krisna.feature_dashboard.databinding.FragmentDashboardBinding
import dev.krisna.feature_dashboard.viewmodel.DashboardMenuItem
import dev.krisna.feature_dashboard.viewmodel.DashboardViewModel
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()

    @Inject
    lateinit var productNavigation: ProductNavigation

    private lateinit var menuAdapter: DashboardMenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        menuAdapter = DashboardMenuAdapter { menuItem ->
            // Di sini kita akan handle navigasi ketika item menu di-klik
            handleMenuClick(menuItem)
        }
        binding.rvDashboardMenu.adapter = menuAdapter
    }

    private fun observeViewModel() {
        viewModel.menuItems.observe(viewLifecycleOwner) { menus ->
            menuAdapter.submitList(menus)
        }
    }

    private fun handleMenuClick(menuItem: DashboardMenuItem) {
        when (menuItem.id) {
//            1 -> findNavController().navigate(R.id.action_dashboardFragment_to_clientsGraph)
            2 -> {
                productNavigation.navigateToProductList()
            }
//            3 -> findNavController().navigate(R.id.action_dashboardFragment_to_ordersGraph)
//            4 -> Toast.makeText(requireContext(), "Ini halaman Dashboard", Toast.LENGTH_SHORT).show()
//            // tambahkan case lainnya
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}