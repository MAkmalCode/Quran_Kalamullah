package com.malbyte.qurankalamullah.feature_quran.data.data_source.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(): Flow<LocationTrackerCondition<Location?>>
}