package com.malbyte.qurankalamullah.presentation.home_screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malbyte.qurankalamullah.feature_quran.domain.utils.toTimeDateString
import com.malbyte.qurankalamullah.ui.theme.Primary
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BookmarkItems(
    surahName: String,
    ayahNumber: Int,
    previewText: String,
    goToRead: () -> Unit,
    timeAdded: Long,
    delete: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            goToRead()
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            IconButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { delete() }
            ) {

                Icon(imageVector = Icons.Rounded.Delete, contentDescription = "")
            }
            Divider(
                color = Primary
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text(
                        text = surahName,
                        fontSize = 24.sp,
                    )
                    Text(
                        text = "Ayah: $ayahNumber",
                        fontSize = 12.sp,
                        textAlign = TextAlign.End,
                    )
                }
                Text(
                    text = timeAdded.toTimeDateString(),
                    fontSize = 12.sp,
                    modifier = Modifier.weight(0.5f),
                    textAlign = TextAlign.End,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                color = Primary
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = previewText,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

//@Preview()
//@Composable
//fun BookmarkItemsPrev() {
//    BookmarkItems()
//}