package com.bytepuppet.freenowfleet.data.entities

import com.google.gson.annotations.SerializedName

data class PoiList(
    @SerializedName("poiList") val poiList: ArrayList<Poi>
)
