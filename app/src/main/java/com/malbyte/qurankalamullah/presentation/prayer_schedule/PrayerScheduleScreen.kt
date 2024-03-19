package com.malbyte.qurankalamullah.presentation.prayer_schedule

import android.Manifest
import android.location.Geocoder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.malbyte.qurankalamullah.feature_quran.data.SettingPreference
import com.malbyte.qurankalamullah.presentation.prayer_schedule.components.PrayerItem
import com.malbyte.qurankalamullah.presentation.prayer_schedule.state.ErrorType
import com.malbyte.qurankalamullah.presentation.prayer_schedule.state.PrayerScheduleState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.Locale.getDefault

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Destination
@Composable
fun PrayerSchedule(
    viewModel: PrayerViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val context = LocalContext.current
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    if (!locationPermissions.allPermissionsGranted) {
        LaunchedEffect(true) {
            locationPermissions.launchMultiplePermissionRequest()
        }
    }

    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        if (locationPermissions.allPermissionsGranted) {
            viewModel.getLocationUpdates()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = if(SettingPreference.currentLang == SettingPreference.INDONESIA) "Jadwal Adzan" else "Adzan Schedule")},
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            viewModel.currentLocation.collectAsState().let {
                @Suppress("DEPRECATION") val address = Geocoder(
                    context,
                    getDefault()
                ).getFromLocation(
                    it.value.latitude,
                    it.value.longitude,
                    1,
                )
                if (!address.isNullOrEmpty()) {
                    val locality = address.first().locality
                    val subLocality = address.first().subLocality
                    val subAdminArea = address.first().subAdminArea
                    val currentLocation = "$locality, $subLocality, $subAdminArea"
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = currentLocation,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
            Text(text = if (SettingPreference.currentLang == SettingPreference.INDONESIA) "Jadwal Adzan" else "Adzan Schedule")
            Spacer(modifier = Modifier.height(20.dp))


            viewModel.adzanScheduleState.collectAsState(PrayerScheduleState.Idle)
                .let { state ->
                    when (val event = state.value) {
                        is PrayerScheduleState.Idle -> {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Column(
                                    modifier = Modifier.align(Alignment.Center),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Nyalakan lokasi",
                                        style = MaterialTheme.typography.titleMedium,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(24.dp))
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        is PrayerScheduleState.Error -> {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {

                                Column(
                                    modifier = Modifier.align(Alignment.Center),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = when (event.errorType) {
                                            ErrorType.NO_GPS -> "GPS Error"
                                            ErrorType.PERMISSION_ERROR -> "Permisioon Missing"
                                            ErrorType.OTHERS -> "Error"
                                        },
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Button(onClick = { viewModel.getLocationUpdates() }) {
                                        Text(text = "Retry?")
                                    }
                                }
                            }
                        }

                        is PrayerScheduleState.Success -> {
                            val sholatTime = event.data
                            PrayerItem(prayerName = "Shubuh", prayerTime = sholatTime.fajr)
                            Spacer(modifier = Modifier.height(16.dp))
                            PrayerItem(prayerName = "Dzuhur", prayerTime = sholatTime.dhuhr)
                            Spacer(modifier = Modifier.height(16.dp))
                            PrayerItem(prayerName = "Ashar", prayerTime = sholatTime.asr)
                            Spacer(modifier = Modifier.height(16.dp))
                            PrayerItem(prayerName = "Maghrib", prayerTime = sholatTime.maghrib)
                            Spacer(modifier = Modifier.height(16.dp))
                            PrayerItem(prayerName = "Isya", prayerTime = sholatTime.isha)
                        }
                    }
                }
        }
    }
}