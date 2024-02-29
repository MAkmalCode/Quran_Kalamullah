package com.malbyte.qurankalamullah.presentation.read_screen

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malbyte.qurankalamullah.feature_quran.data.SettingPreference
import com.malbyte.qurankalamullah.feature_quran.domain.model.Bookmark
import com.malbyte.qurankalamullah.feature_quran.domain.model.Quran
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.QuranUseCases
import com.malbyte.qurankalamullah.presentation.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import snow.player.PlayMode
import snow.player.PlayerClient
import snow.player.audio.MusicItem
import snow.player.playlist.Playlist
import javax.inject.Inject

@HiltViewModel
class ReadViewModel @Inject constructor(
    private val quranUseCases: QuranUseCases,
    private val playerClient: PlayerClient,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val navArgs: ReadArg = savedStateHandle.navArgs()

    private val _listAyahState = MutableStateFlow<List<Quran>>(emptyList())
    val listAyahState = _listAyahState.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), emptyList())

    val lastToPost = mutableIntStateOf(navArgs.position)

    private val qoriId = SettingPreference.listQori[SettingPreference.currentQori].qoriId
    private val qoriName = SettingPreference.listQori[SettingPreference.currentQori].qoriName

    fun insertBookmak(bookmark: Bookmark) {
        viewModelScope.launch {
            quranUseCases.insertBookmarkUseCase(bookmark)
        }
    }

    val isPLaying = mutableStateOf(IsPlaying.NOT_PLAYING)
    val quran = mutableStateOf<Quran?>(null)

    fun onEvent(event: PlayerEvent) {
        when (event) {
            is PlayerEvent.PlayAll -> {
                playerClient.stop()
                playerClient.connect {
                    val playList = allPlayList(event.quranList)
                    playerClient.setPlaylist(playList, true)
                    isPLaying.value = IsPlaying.PLAY_ALL
                    playerClient.addOnPlayingMusicItemChangeListener { _, position, _ ->
                        quran.value = event.quranList[position]
                    }
                }
            }

            is PlayerEvent.PlayAyah -> {
                playerClient.stop()
                playerClient.connect {
                    val playList = onePlayList(event.quran)
                    playerClient.setPlaylist(playList, true)
                    quran.value = event.quran
                    isPLaying.value = IsPlaying.PLAYING
                    playerClient.playMode = PlayMode.SINGLE_ONCE
                }
            }

            is PlayerEvent.Previous -> {
                playerClient.skipToPrevious()
            }

            is PlayerEvent.Next -> {
                playerClient.skipToNext()
            }

            is PlayerEvent.Stop -> {
                playerClient.stop()
            }

            PlayerEvent.PausePlayAyah -> {
                playerClient.playPause()
                playerClient.playMode = PlayMode.SINGLE_ONCE
                playerClient.addOnWaitPlayCompleteChangeListener {
                    if (it){
                        isPLaying.value = IsPlaying.PAUSE
                    }
                }
                isPLaying.value = if(isPLaying.value == IsPlaying.PAUSE){
                    IsPlaying.PLAYING
                }else{
                    IsPlaying.PAUSE
                }

            }
        }
    }

    private fun allPlayList(
        listQuran: List<Quran>
    ): Playlist {
        val musicItemList = mutableListOf<MusicItem>()

        listQuran.forEach {
            val formattedSurahNumber = String.format("%03d", it.surah)
            val formattedAyahNumber = String.format("%03d", it.ayahNumb)

            musicItemList.add(
                MusicItem.Builder()
                    .setTitle("${it.surahNameEmlaey}: ${it.ayahNumb}")
                    .setArtist(qoriName)
                    .setUri("https://everyayah.com/data/$qoriId/$formattedSurahNumber$formattedAyahNumber.mp3")
                    .setIconUri("https://iqna.ir/files/id/news/2023/12/26/50089_989.jpg")
                    .autoDuration()
                    .build()
            )
        }

        return Playlist.Builder()
            .appendAll(musicItemList)
            .build()
    }

    private fun onePlayList(
        quran: Quran
    ): Playlist {

        val formattedSurahNumber = String.format("%03d", quran.surah)
        val formattedAyahNumber = String.format("%03d", quran.ayahNumb)


        return Playlist.Builder()
            .append(
                MusicItem.Builder()
                    .setTitle("${quran.surahNameEmlaey}: ${quran.ayahNumb}")
                    .setArtist(qoriName)
                    .setUri("https://everyayah.com/data/$qoriId/$formattedSurahNumber$formattedAyahNumber.mp3")
                    .setIconUri("https://iqna.ir/files/id/news/2023/12/26/50089_989.jpg")
                    .autoDuration()
                    .build()
            )
            .build()
    }

    private fun load() {

        when (navArgs.readType) {
            0 -> {
                quranUseCases.getReadSurahUsecase(navArgs.surahNumb ?: 0).onEach { listSurah ->
                    _listAyahState.emit(listSurah)
                }.launchIn(viewModelScope)
            }

            1 -> {
                quranUseCases.getReadJuzUseCase(navArgs.juzNumb ?: 0).onEach { listJuz ->
                    _listAyahState.emit(listJuz)
                }.launchIn(viewModelScope)
            }

            2 -> {
                quranUseCases.getReadSurahUsecase(navArgs.surahNumb ?: 0).onEach { listSurah ->
                    _listAyahState.emit(listSurah)
                }.launchIn(viewModelScope)
            }
        }
    }

    init {
        load()
    }

    sealed class PlayerEvent {
        data class PlayAyah(val quran: Quran) : PlayerEvent()
        data object PausePlayAyah : PlayerEvent()
        data class PlayAll(val quranList: List<Quran>) : PlayerEvent()
        data object Next : PlayerEvent()
        data object Previous : PlayerEvent()
        data object Stop : PlayerEvent()
    }
}

enum class IsPlaying{
    PLAYING,
    NOT_PLAYING,
    PAUSE,
    PLAY_ALL,
}
