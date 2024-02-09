package com.malbyte.qurankalamullah.presentation.read_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import javax.inject.Inject

@HiltViewModel
class ReadViewModel @Inject constructor(
    private val quranUseCases: QuranUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val navArgs: ReadArg = savedStateHandle.navArgs()

    private val _listAyahState = MutableStateFlow<List<Quran>>(emptyList())
    val listAyahState = _listAyahState.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), emptyList())

    val lastToPost = mutableStateOf(navArgs.position)

    fun insertBookmak(bookmark: Bookmark){
        viewModelScope.launch {
            quranUseCases.insertBookmarkUseCase(bookmark)
        }
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

            2->{
                quranUseCases.getReadSurahUsecase(navArgs.surahNumb ?: 0).onEach { listSurah ->
                    _listAyahState.emit(listSurah)
                }.launchIn(viewModelScope)
            }
        }
    }

    init {
        load()
    }
}