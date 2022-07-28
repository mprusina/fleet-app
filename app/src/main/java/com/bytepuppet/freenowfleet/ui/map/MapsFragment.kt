package com.bytepuppet.freenowfleet.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bytepuppet.freenowfleet.R
import com.bytepuppet.freenowfleet.ui.FleetViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Map Fragment for displaying vehicle fleet on map
 */
class MapsFragment : Fragment() {

    private val fleetViewModel: FleetViewModel by activityViewModels()

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.setMinZoomPreference(7.0f)
        googleMap.uiSettings.isZoomControlsEnabled = true

        if (fleetViewModel.getSelectedVehicle() != null) {
            val vehicle = fleetViewModel.getSelectedVehicle()
            val vehicleLocation =
                LatLng(vehicle!!.coordinate.latitude, vehicle.coordinate.longitude)
            googleMap.addMarker(MarkerOptions().position(vehicleLocation).title(vehicle.fleetType))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(vehicleLocation))
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(17.0f))
            fleetViewModel.clearSelectedVehicle()
        } else {
            val hamburg = LatLng(53.5511, 9.9937)
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(hamburg))
            val vehicleList = fleetViewModel.getData().poiList
            for (vehicle in vehicleList) {
                val vehicleLocation =
                    LatLng(vehicle.coordinate.latitude, vehicle.coordinate.longitude)
                googleMap.addMarker(
                    MarkerOptions().position(vehicleLocation).title(vehicle.fleetType)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}