package com.malbyte.qurankalamullah.presentation.tab_screen.juz_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.malbyte.qurankalamullah.ui.theme.Primary

@Composable
fun JuzItem(
    juzNumb: Int?,
    listSurahJuz: List<String?>,
    surahNumblist: List<Int?>
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Primary),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Juz $juzNumb",
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(12.dp))
            SurahJuzItem(listSurahJuz = listSurahJuz, surahNumber = surahNumblist)
        }
    }
}

