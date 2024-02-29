package com.malbyte.qurankalamullah.presentation.home_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malbyte.qurankalamullah.feature_quran.domain.model.Bookmark
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.QuranUseCases
import com.malbyte.qurankalamullah.presentation.home_screen.state.JuzState
import com.malbyte.qurankalamullah.presentation.home_screen.state.ListQuranState
import com.malbyte.qurankalamullah.presentation.home_screen.state.SurahState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quranUseCases: QuranUseCases
): ViewModel() {

    private val _surahState = mutableStateOf(SurahState())
    val surahState = _surahState

    private val _juzState = MutableStateFlow(JuzState())
    val juzState = _juzState.asStateFlow()

    private val _searchSurahState = MutableStateFlow(ListQuranState())
    val searchSurahState = _searchSurahState.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun searchResult(text: String){
        quranUseCases.searchSurahUseCase(text).onEach {listQuran ->
            _searchSurahState.emit(ListQuranState(listQuran))
        }.launchIn(viewModelScope)
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

    private var getSurahJob: Job? = null
    private var getJuzJob: Job? = null

    val bookmarkState =
        quranUseCases.getBookmarkUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500),
            emptyList()
        )

    fun deleteBookmmark(bookmark: Bookmark){
        viewModelScope.launch {
            quranUseCases.deleteBookmark(bookmark)
        }
    }

    fun deleteAllBookmark(){
        viewModelScope.launch {
            quranUseCases.deleteAllBookmark()
        }
    }

    private fun quranList(){
        getSurahJob?.cancel()
        getJuzJob?.cancel()

        getSurahJob = quranUseCases.getSurahUseCase()
            .onEach { surah ->
                _surahState.value = (SurahState(surah))
            }.launchIn(viewModelScope)

        getJuzJob = quranUseCases.getJuzUseCase()
            .onEach { juz ->
                _juzState.emit(JuzState(juz))
            }.launchIn(viewModelScope)
    }

    init {
        quranList()
    }

//    sealed class BookmarkEvent {
//        data object DeleteAllBookmark : BookmarkEvent()
//        data class DeletetBookmark(val bookmark: Bookmark) : BookmarkEvent()
//    }
}

