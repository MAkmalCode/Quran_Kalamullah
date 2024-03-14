package com.malbyte.qurankalamullah.presentation.prayer_schedule.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malbyte.qurankalamullah.ui.theme.Primary

@Composable
fun PrayerItem(
    prayerName: String,
    prayerTime: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Primary,
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = prayerName,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = prayerTime,
                fontSize = 15.sp
                )
        }
    }
}

@Preview
@Composable
fun wkwkland() {
    PrayerItem(prayerName = "Dzuhur", prayerTime = "12:06")
}