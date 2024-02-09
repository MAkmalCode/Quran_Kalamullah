package com.malbyte.qurankalamullah.presentation.home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malbyte.qurankalamullah.R
import com.malbyte.qurankalamullah.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuzItem(
    juzNumb: Int?,
    listSurahJuz: List<String?>,
    surahNumblist: List<Int?>,
    goToRead: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Primary),
        elevation = CardDefaults.cardElevation(4.dp),
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
            SurahJuzItem(listSurahJuz = listSurahJuz, surahNumber = surahNumblist, goToRead = {goToRead()})
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahJuzItem(
    surahNumber: List<Int?>,
    listSurahJuz: List<String?>,
    goToRead: () -> Unit
) {

    for (index in listSurahJuz.indices){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            onClick = {
                goToRead()
            }
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.numb_image),
                        contentDescription = "",
                        modifier = Modifier.size(55.dp)
                    )
                    Text(
                        text = surahNumber[index].toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = listSurahJuz[index].toString(),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

