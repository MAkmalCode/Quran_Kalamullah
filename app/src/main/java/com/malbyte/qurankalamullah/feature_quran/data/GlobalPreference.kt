package com.malbyte.qurankalamullah.feature_quran.data

import com.chibatching.kotpref.KotprefModel

object GlobalPreference: KotprefModel() {
    var firstTime by intPref(0)
}