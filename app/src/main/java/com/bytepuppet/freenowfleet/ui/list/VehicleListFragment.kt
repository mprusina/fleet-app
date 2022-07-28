package com.bytepuppet.freenowfleet.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bytepuppet.freenowfleet.R
import com.bytepuppet.freenowfleet.data.entities.Poi
import com.bytepuppet.freenowfleet.databinding.FragmentVehicleListBinding
import com.bytepuppet.freenowfleet.ui.FleetViewModel
import com.bytepuppet.freenowfleet.ui.VehicleListViewState
import com.bytepuppet.freenowfleet.ui.list.utils.VehicleListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * A fragment for displaying a list of vehicles.
 */
@AndroidEntryPoint
class VehicleListFragment : Fragment(), VehicleListAdapter.OnMyClickListener {

    private val fleetViewModel: FleetViewModel by activityViewModels()
    private lateinit var binding: FragmentVehicleListBinding
    private lateinit var vehicleAdapter: VehicleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVehicleListBinding.inflate(inflater, container, false)
        vehicleAdapter = VehicleListAdapter(this)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                fleetViewModel.viewState.collect { viewState ->
                    when (viewState) {
                        is VehicleListViewState.Success -> {
                            binding.swipe.isRefreshing = false
                            binding.loadingIndicator.visibility = View.GONE
                            binding.errorMessage.visibility = View.GONE
                            vehicleAdapter.submitList(viewState.vehicleList?.poiList)
                        }
                        VehicleListViewState.Error -> {
                            binding.swipe.isRefreshing = false
                            binding.loadingIndicator.visibility = View.GONE
                            binding.errorMessage.visibility = View.VISIBLE
                        }
                        VehicleListViewState.Loading -> {
                            binding.loadingIndicator.visibility = View.VISIBLE
                            binding.errorMessage.visibility = View.GONE
                        }
                    }
                }
            }
        }

        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = vehicleAdapter
        }

        binding.swipe.apply {
            setOnRefreshListener {
                binding.swipe.isRefreshing = true
                binding.loadingIndicator.visibility = View.GONE
                fleetViewModel.refresh()
            }
        }

        return binding.root
    }

    override fun onItemClick(position: Int, item: Poi) {
        fleetViewModel.setSelectedVehicle(item)
        findNavController().navigate(R.id.action_navigation_list_to_navigation_map)
    }
}