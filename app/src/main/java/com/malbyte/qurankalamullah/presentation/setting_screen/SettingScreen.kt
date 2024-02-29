package com.malbyte.qurankalamullah.presentation.setting_screen

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
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        var showDialog by remember {
            mutableStateOf(false)
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            onClick = {
                showDialog = true
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (showDialog == true) {

                    MinimalDialog(
                        listQori = SettingPreference.listQori,
                        onDismissRequest = {
                            showDialog = false
                        }
                    )
                }
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
                        text = "Current Qori",
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
    }
}

@Composable
fun MinimalDialog(
    onDismissRequest: () -> Unit,
    listQori: List<SettingPreference.Qori>
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPrev() {
    SettingScreen()
}