package com.malbyte.qurankalamullah.presentation.prayer_schedule.state

import com.malbyte.qurankalamullah.feature_quran.data.data_source.remote.model.Time


sealed class PrayerScheduleState {
    data class Success(val data: Time) : PrayerScheduleState()
    data class Error(val errorType: ErrorType) : PrayerScheduleState()
    data object Idle: PrayerScheduleState()
}

enum class ErrorType {
    NO_GPS,
    PERMISSION_ERROR,
    OTHERS
}