package com.malbyte.qurankalamullah.feature_quran.data.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(): Flow<LocationTrackerCondition<Location?>>
}