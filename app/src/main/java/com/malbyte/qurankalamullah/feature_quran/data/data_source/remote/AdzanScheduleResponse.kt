package com.malbyte.qurankalamullah.feature_quran.data.data_source.remote

import com.google.gson.annotations.SerializedName
import com.malbyte.qurankalamullah.feature_quran.data.data_source.remote.model.Time

data class AdzanScheduleResponse(
    @SerializedName("city")
    val city: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("province")
    val province: String,
    @SerializedName("times")
    val times: List<Time>
)