package com.malbyte.qurankalamullah.presentation.home_screen.state

import com.malbyte.qurankalamullah.feature_quran.domain.model.Juz
data class JuzState(
    val listJuz: List<Juz> = emptyList()
)
