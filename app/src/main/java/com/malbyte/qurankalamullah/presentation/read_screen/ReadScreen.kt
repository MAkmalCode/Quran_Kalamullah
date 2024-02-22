package com.malbyte.qurankalamullah.presentation.read_screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
    val position = viewModel.lastToPost.value!!
    val lazyColumnState = rememberLazyListState()
    LaunchedEffect(true) {
        lazyColumnState.scrollToItem(position)
    }
    val isPlayingState = viewModel.isPLaying

    Scaffold(
        bottomBar = {
            if (isPlayingState.value == IsPlaying.PLAYING || isPlayingState.value == IsPlaying.PAUSE){
                PlayerControlPanel(
                    surahName = viewModel.quran.value?.surahNameEmlaey!!,
                    ayahNumber = viewModel.quran.value?.ayahNumb!!,
                    qori = "Abdul basit samad",
                    next = {
                        viewModel.onEvent(ReadViewModel.PlayerEvent.Next)
                    },
                    previous = {
                        viewModel.onEvent(ReadViewModel.PlayerEvent.Previous)
                    },
                    play = {
                        viewModel.onEvent(ReadViewModel.PlayerEvent.PlayAyah())
                    },
                    pause = {
                        viewModel.onEvent(ReadViewModel.PlayerEvent.Pause)
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
                            translateId = quran.translationIndo,
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
                                    "Telah menambahkan bookmark",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            copy = {
                                copyToClipboard(
                                    context,
                                    "Surah: ${quran.surahNameEmlaey}\nAyat: ${quran.ayahNumb}\n${quran.ayahText}\n${quran.translationIndo}"
                                )
                                Toast.makeText(context, "Copy ke clipboard", Toast.LENGTH_SHORT)
                                    .show()
                            },
                            share = {
                                context.startActivity(shareButton("Surah: ${quran.surahNameEmlaey}\nAyat: ${quran.ayahNumb}\n${quran.ayahText}\n${quran.translationIndo}"))
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