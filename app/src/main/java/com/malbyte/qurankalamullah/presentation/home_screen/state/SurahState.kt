package com.malbyte.qurankalamullah.presentation.tab_screen.surah_screen.state

import com.malbyte.qurankalamullah.feature_quran.domain.model.Surah

sealed class SurahState {
    data object Loading : SurahState()
    data class Error(val message: String) : SurahState()
    data class Success(val listSurah: List<Surah>) : SurahState()
}