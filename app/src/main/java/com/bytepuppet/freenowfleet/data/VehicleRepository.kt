package com.bytepuppet.freenowfleet.data

import android.location.Geocoder
import com.bytepuppet.freenowfleet.data.api.VehicleService
import com.bytepuppet.freenowfleet.data.entities.PoiList
import javax.inject.Inject

class VehicleRepository @Inject constructor(
    private val vehicleService: VehicleService,
    private val geocoder: Geocoder
) {
    suspend fun getHamburgFleet(): PoiList {
        return getStreetFromLocation(vehicleService.getHamburgFleet())
    }

    private fun getStreetFromLocation(poiList: PoiList): PoiList {
        for (vehicle in poiList.poiList) {
            val street = geocoder.getFromLocation(
                vehicle.coordinate.latitude,
                vehicle.coordinate.longitude,
                1
            )
            vehicle.street = street[0].getAddressLine(0)
        }
        return poiList
    }
}