package com.malbyte.qurankalamullah.presentation.setting_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Translate
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.malbyte.qurankalamullah.feature_quran.data.SettingPreference
import com.malbyte.qurankalamullah.ui.theme.Primary
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SettingScreen() {

    var settingState by remember {
        mutableIntStateOf(0)
    }

    var langState by remember {
        mutableStateOf(SettingPreference.currentLang)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        var showDialog by remember {
            mutableStateOf(false)
        }
        if (showDialog == true) {

            MinimalDialog(
                listQori = SettingPreference.listQori,
                listLanguage = SettingPreference.listLanguage,
                settingState = settingState,
                onDismissRequest = {
                    showDialog = false
                    langState = SettingPreference.currentLang
                }
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            onClick = {
                showDialog = true
                settingState = 0
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Rounded.VolumeUp,
                        contentDescription = "",
                        tint = Primary
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if(langState == SettingPreference.INDONESIA) "Qori Sekarang" else "Current Qori",
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = SettingPreference.listQori[SettingPreference.currentQori].qoriName,
                    color = Color.Gray
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            onClick = {
                showDialog = true
                settingState = 1
                Log.d("check state lang", SettingPreference.listLanguage[SettingPreference.currentLang])
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Rounded.Translate,
                        contentDescription = "",
                        tint = Primary
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if(langState == SettingPreference.INDONESIA) "Bahasa Sekarang" else "Current Language",
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = SettingPreference.listLanguage[langState],
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun MinimalDialog(
    onDismissRequest: () -> Unit,
    listQori: List<SettingPreference.Qori>,
    listLanguage: List<String>,
    settingState: Int
) {

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            if (settingState == 0){
                listQori.forEachIndexed { index, qori ->
                    TextButton(
                        onClick = {
                            SettingPreference.currentQori = index
                            onDismissRequest()
                        },
                    ) {
                        Text(text = qori.qoriName)
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }else{
                listLanguage.forEachIndexed { index, lang ->
                    TextButton(
                        onClick = {
                            SettingPreference.currentLang = index
                            onDismissRequest()
                        },
                    ) {
                        Text(text = lang)
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPrev() {
    SettingScreen()
}