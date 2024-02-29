package com.malbyte.qurankalamullah.presentation.home_screen.state

import com.malbyte.qurankalamullah.feature_quran.domain.model.Surah

data class ListQuranState(
    val listQuran: List<Surah> = emptyList()
)
