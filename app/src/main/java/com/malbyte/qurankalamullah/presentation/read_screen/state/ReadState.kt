package com.malbyte.qurankalamullah.presentation.read_screen.state

import com.malbyte.qurankalamullah.feature_quran.domain.model.Quran

sealed class ReadState {
    data object Loading : ReadState()
    data class Error(val message: String) : ReadState()
    data class Success(val listSurah: List<Quran>) : ReadState()
}