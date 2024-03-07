package com.malbyte.qurankalamullah.presentation.read_screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.malbyte.qurankalamullah.feature_quran.data.LastRead
import com.malbyte.qurankalamullah.feature_quran.data.SettingPreference
import com.malbyte.qurankalamullah.feature_quran.domain.model.Bookmark
import com.malbyte.qurankalamullah.presentation.GlobalViewModel
import com.malbyte.qurankalamullah.presentation.components.PlayerControlPanel
import com.malbyte.qurankalamullah.presentation.read_screen.components.Readitem
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination(navArgsDelegate = ReadArg::class)
fun ReadScreen(
    viewModel: ReadViewModel = hiltViewModel(),
    globalViewModel: GlobalViewModel
) {
    val context = LocalContext.current
    val ayahList by viewModel.listAyahState.collectAsStateWithLifecycle()
    val getTotalAyahList = remember {
        globalViewModel.getTotalAyah()
    }
    val position = viewModel.lastToPost.intValue!!
    val lazyColumnState = rememberLazyListState()
    LaunchedEffect(true) {
        lazyColumnState.scrollToItem(position)
    }
    val isPlayingState = viewModel.isPLaying
    val quran = viewModel.quran.value

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(onClick = {
//                viewModel.onEvent(ReadViewModel.PlayerEvent.PlayAll())
            }) {
                Icon(
                    imageVector = if (isPlayingState.value == IsPlaying.PAUSE) Icons.Rounded.PlayArrow else Icons.Rounded.Pause,
                    contentDescription = ""
                )
            }
        },
        bottomBar = {
            if (isPlayingState.value == IsPlaying.PLAYING || isPlayingState.value == IsPlaying.PAUSE) {
                PlayerControlPanel(
                    surahName = quran?.surahNameEmlaey ?: "",
                    ayahNumber = quran?.ayahNumb ?: 1,
                    qori = SettingPreference.listQori[SettingPreference.currentQori].qoriName,
                    next = {
                        viewModel.onEvent(ReadViewModel.PlayerEvent.Next)
                    },
                    previous = {
                        viewModel.onEvent(ReadViewModel.PlayerEvent.Previous)
                    },
                    play = {
                        viewModel.onEvent(ReadViewModel.PlayerEvent.PausePlayAyah)
                    },
                    stop = {
                        viewModel.onEvent(ReadViewModel.PlayerEvent.Stop)
                    },
                    isPaused = isPlayingState.value == IsPlaying.PAUSE
                )
            }
        },
        content = {

            LazyColumn(
                modifier = Modifier.padding(it),
                state = lazyColumnState,
                content = {
                    items(ayahList.size) { index ->
                        val quran = ayahList[index]
                        LastRead.surahNumber = quran.surah!!
                        LastRead.position = index
                        LastRead.surahName = quran.surahNameEmlaey!!
                        LastRead.ayahNumber = quran.ayahNumb!!
                        Readitem(
                            ayahText = quran.ayahText,
                            ayahNumb = quran.ayahNumb,
                            translateId = if (SettingPreference.currentLang == SettingPreference.INDONESIA) quran.translationIndo else quran.translationEng,
                            countAyah = getTotalAyahList[quran.surah!! - 1],
                            descentPlace = quran.descendPlace!!,
                            surahName = quran.surahNameEmlaey!!,
                            juz = quran.juz!!,
                            bookMark = {
                                viewModel.insertBookmak(
                                    Bookmark(
                                        surahName = quran.surahNameEmlaey,
                                        surahNumber = quran.surah,
                                        ayahNumber = quran.ayahNumb!!,
                                        ayahText = quran.ayahText!!,
                                        position = index,
                                        timeAdded = System.currentTimeMillis(),
                                        id = quran.id
                                    )
                                )
                                Toast.makeText(
                                    context,
                                    if (SettingPreference.currentLang == SettingPreference.INDONESIA) "Telah menambahkan bookmark" else "Added to bookmark",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            copy = {
                                copyToClipboard(
                                    context,
                                    "Surah: ${quran.surahNameEmlaey}\nAyat: ${quran.ayahNumb}\n${quran.ayahText}\n${if (SettingPreference.currentLang == SettingPreference.INDONESIA) quran.translationIndo else quran.translationEng}"
                                )
                                Toast.makeText(context, if(SettingPreference.currentLang == SettingPreference.INDONESIA) "Copy ke clipboard" else "Copy to clipboard", Toast.LENGTH_SHORT)
                                    .show()
                            },
                            share = {
                                context.startActivity(shareButton("Surah: ${quran.surahNameEmlaey}\nAyat: ${quran.ayahNumb}\n${quran.ayahText}\n${if (SettingPreference.currentLang == SettingPreference.INDONESIA) quran.translationIndo else quran.translationEng}"))
                            },
                            playAyah = {
                                viewModel.onEvent(ReadViewModel.PlayerEvent.PlayAyah(quran))
                            }
                        )
                    }
                })
        })
}

fun copyToClipboard(context: Context, text: String) {
    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("password", text)
    clipboardManager.setPrimaryClip(clip)
}

fun shareButton(text: String): Intent {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    return Intent.createChooser(sendIntent, null)
}

data class ReadArg(
    val readType: Int = 0,
    val surahNumb: Int? = null,
    val juzNumb: Int? = null,
    val position: Int
)