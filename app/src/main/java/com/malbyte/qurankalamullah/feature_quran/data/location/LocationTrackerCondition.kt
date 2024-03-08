package com.malbyte.qurankalamullah.feature_quran.data.location

import android.location.Location

sealed class LocationTrackerCondition<T> {
    class NoGps<Nothing>: LocationTrackerCondition<Nothing>()
    class MissingPermission<Nothing>: LocationTrackerCondition<Nothing>()
    data class Success<T>(val location: Location?): LocationTrackerCondition<T>()
    class Error<T>: LocationTrackerCondition<T>()
}