package com.malbyte.qurankalamullah.presentation.prayer_schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malbyte.qurankalamullah.feature_quran.data.location.LocationClient
import com.malbyte.qurankalamullah.feature_quran.data.location.LocationTrackerCondition
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.QuranUseCases
import com.malbyte.qurankalamullah.presentation.prayer_schedule.state.ErrorType
import com.malbyte.qurankalamullah.presentation.prayer_schedule.state.PrayerScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrayerViewModel @Inject constructor(
    private val useCases: QuranUseCases,
    private val locationClient: LocationClient
) : ViewModel() {

    private val _adzanScheduleState: MutableStateFlow<PrayerScheduleState> =
        MutableStateFlow(PrayerScheduleState.Idle)
    val adzanScheduleState = _adzanScheduleState.asStateFlow()

    private val _currentLocation: MutableStateFlow<CurrentLocation> =
        MutableStateFlow(CurrentLocation(0.0, 0.0))
    val currentLocation: StateFlow<CurrentLocation> = _currentLocation.asStateFlow()

    fun getLocationUpdates() {
        viewModelScope.launch {
            _adzanScheduleState.emit(PrayerScheduleState.Idle)
            locationClient.getLocationUpdates()
                .onEach { tracker ->
                    when (tracker) {
                        is LocationTrackerCondition.Error -> {
                            _adzanScheduleState.emit(PrayerScheduleState.Error(ErrorType.OTHERS))
                        }

                        is LocationTrackerCondition.MissingPermission -> {
                            _adzanScheduleState.emit(PrayerScheduleState.Error(ErrorType.PERMISSION_ERROR))
                        }

                        is LocationTrackerCondition.NoGps -> {
                            _adzanScheduleState.emit(PrayerScheduleState.Error(ErrorType.NO_GPS))
                        }

                        is LocationTrackerCondition.Success -> {
                            val latitude = tracker.location?.latitude
                            val longitude = tracker.location?.longitude
                            if (latitude == null || longitude == null) {
                                _adzanScheduleState.emit(PrayerScheduleState.Error(ErrorType.OTHERS))
                                return@onEach
                            }
                            _currentLocation.emit(CurrentLocation(longitude, latitude))
                            val responseResult = useCases.getPrayerScheduleUseCase(
                                latitude.toString(),
                                longitude.toString()
                            )
                            _adzanScheduleState.emit(PrayerScheduleState.Success(responseResult.times[0]))
                            cancel()
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    data class CurrentLocation(
        val longitude: Double,
        val latitude: Double
    )
}