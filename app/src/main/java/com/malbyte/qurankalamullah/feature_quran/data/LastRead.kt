package com.malbyte.qurankalamullah.feature_quran.data

import com.chibatching.kotpref.KotprefModel

object LastRead:KotprefModel() {
    var surahNumber by intPref()
    var position by intPref()
    var surahName by stringPref()
    var ayahNumber by intPref()
}