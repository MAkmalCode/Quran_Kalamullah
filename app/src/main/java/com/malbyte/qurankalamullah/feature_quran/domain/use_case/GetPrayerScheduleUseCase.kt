package com.malbyte.qurankalamullah.feature_quran.domain.use_case

import com.malbyte.qurankalamullah.feature_quran.data.data_source.remote.AdzanScheduleResponse
import com.malbyte.qurankalamullah.feature_quran.data.data_source.remote.ApiInterface

class GetPrayerScheduleUseCase(
    private val apiInterface: ApiInterface
) {

    suspend operator fun invoke(latitude: String, longtitude: String): AdzanScheduleResponse{
        return apiInterface.getAdzanSchedule(latitude, longtitude)
    }
}