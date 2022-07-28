package com.bytepuppet.freenowfleet.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytepuppet.freenowfleet.data.VehicleRepository
import com.bytepuppet.freenowfleet.data.entities.Poi
import com.bytepuppet.freenowfleet.data.entities.PoiList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FleetViewModel @Inject constructor(private val repository: VehicleRepository) : ViewModel() {

    private val _viewState =
        MutableStateFlow<VehicleListViewState>(VehicleListViewState.Success(null))
    val viewState: StateFlow<VehicleListViewState> = _viewState

    private lateinit var hamburgVehicleFleet: PoiList
    private var selectedVehicle: Poi? = null

    init {
        loadFleet()
    }

    fun refresh() {
        loadFleet()
    }

    private fun loadFleet() {
        _viewState.value = VehicleListViewState.Loading

        viewModelScope.launch {
            runCatching {
                repository.getHamburgFleet()
            }.onFailure {
                _viewState.value = VehicleListViewState.Error
            }.onSuccess { vehicleList ->
                _viewState.value = VehicleListViewState.Success(vehicleList)
                hamburgVehicleFleet = vehicleList
            }
        }
    }

    fun getData(): PoiList {
        return hamburgVehicleFleet
    }

    fun setSelectedVehicle(vehicle: Poi) {
        selectedVehicle = vehicle
    }

    fun getSelectedVehicle(): Poi? {
        return selectedVehicle
    }

    fun clearSelectedVehicle() {
        selectedVehicle = null
    }
}

sealed class VehicleListViewState {
    object Loading : VehicleListViewState()
    object Error : VehicleListViewState()
    data class Success(val vehicleList: PoiList?) : VehicleListViewState()
}