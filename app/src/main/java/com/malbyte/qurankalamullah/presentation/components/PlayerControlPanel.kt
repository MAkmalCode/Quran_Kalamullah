package com.malbyte.qurankalamullah.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malbyte.qurankalamullah.ui.theme.Primary

@Composable
fun PlayerControlPanel(
    surahName: String,
    ayahNumber: Int,
    qori: String,
    next: () -> Unit,
    previous: () -> Unit,
    pause: () -> Unit,
    stop: () -> Unit,
    play: () -> Unit,
    isPaused: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Primary
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                Text(
                    text = "$surahName: $ayahNumber",
                    fontSize = 20.sp
                )
                Text(
                    text = qori,
                    fontSize = 10.sp
                )
            }
            Row(
                modifier = Modifier.weight(0.5f),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(imageVector = Icons.Rounded.SkipPrevious, contentDescription = "")
                }
                IconButton(
                    modifier = Modifier
                        .background(Primary, CircleShape),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        tint = Color.White,
                        contentDescription = ""
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(imageVector = Icons.Rounded.SkipNext, contentDescription = "")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerControlPanelPrev() {
    PlayerControlPanel(
        "",
        1,
        "Abdul basit samad",
        {},
        {},
        {},
        {},
        {},
        false
    )
}