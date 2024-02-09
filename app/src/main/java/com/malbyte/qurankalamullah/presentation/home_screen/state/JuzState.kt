package com.malbyte.qurankalamullah.presentation.tab_screen.juz_screen.state

import com.malbyte.qurankalamullah.feature_quran.domain.model.Juz

sealed class JuzState {
    data object Loading : JuzState()
    data class Error(val message: String) : JuzState()
    data class Success(val listJuz: List<Juz>) : JuzState()
}