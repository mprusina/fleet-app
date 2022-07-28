package com.bytepuppet.freenowfleet.data.entities

import com.google.gson.annotations.SerializedName

data class Poi(
    @SerializedName("id") val id: Int,
    @SerializedName("coordinate") val coordinate: Coordinate,
    @SerializedName("fleetType") val fleetType: String,
    @SerializedName("heading") val heading: Double,
    var street: String
)
