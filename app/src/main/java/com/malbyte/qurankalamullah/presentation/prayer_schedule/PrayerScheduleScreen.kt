package com.malbyte.qurankalamullah.presentation.prayer_schedule

import android.Manifest
import android.location.Geocoder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import java.util.Locale.getDefault

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PrayerSchedule(
    viewModel: PrayerViewModel = hiltViewModel()
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

    Scaffold {
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

        }
    }
}