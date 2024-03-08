package com.malbyte.qurankalamullah.feature_quran.data.data_source.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("day")
    suspend fun getAdzanSchedule(
        @Query("latitude") latitude:String,
        @Query("longitude") longitude:String
    ): AdzanScheduleResponse
}