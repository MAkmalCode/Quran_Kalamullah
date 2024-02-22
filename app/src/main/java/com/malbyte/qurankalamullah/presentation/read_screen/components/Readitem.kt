package com.malbyte.qurankalamullah.presentation.read_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malbyte.qurankalamullah.R
import com.malbyte.qurankalamullah.ui.theme.Primary

@Composable
fun Readitem(
    ayahText: String?,
    ayahNumb: Int?,
    translateId: String?,
    countAyah: Int,
    descentPlace: String,
    surahName: String,
    juz: Int,
    bookMark: () -> Unit,
    copy: () -> Unit,
    share: () -> Unit,
    playAyah: () -> Unit
) {

    var btnState by remember {
        mutableStateOf(false)
    }

    if (ayahNumb == 1) {
        Card(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Primary)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = descentPlace,
                        color = Color.White,

                        )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = surahName,
                        color = Color.White,
                        fontSize = 35.sp
                    )
                    Text(
                        text = "$countAyah Ayat /  Juz $juz",
                        color = Color.White
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(
                    16.dp,
                    16.dp,
                    16.dp
                )
        ) {

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = ayahText ?: "data kosong",
                textAlign = TextAlign.End,
                fontSize = 25.sp,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.uthmanic_hafs))
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = translateId ?: "data kosong"
            )
            IconButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    btnState = !btnState
                }
            ) {
                val icon: ImageVector = if (!btnState) {
                    Icons.Rounded.KeyboardArrowDown
                } else {
                    Icons.Rounded.KeyboardArrowUp
                }
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    tint = Primary,
                )
            }
            AnimatedVisibility(visible = btnState) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        modifier = Modifier.weight(0.25f),
                        onClick = { copy() }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.CopyAll,
                            contentDescription = "",
                            tint = Primary,
                        )
                    }
                    IconButton(
                        modifier = Modifier.weight(0.25f),
                        onClick = { share() }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = "",
                            tint = Primary,
                        )
                    }
                    IconButton(
                        modifier = Modifier.weight(0.25f),
                        onClick = { playAyah() }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.VolumeUp,
                            contentDescription = "",
                            tint = Primary,
                        )
                    }
                    IconButton(
                        modifier = Modifier.weight(0.25f),
                        onClick = {
                            bookMark()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Bookmark,
                            contentDescription = "",
                            tint = Primary,
                        )
                    }
                }
            }
        }
    }
}

