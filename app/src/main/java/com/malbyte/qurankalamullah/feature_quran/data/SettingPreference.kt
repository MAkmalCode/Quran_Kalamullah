package com.malbyte.qurankalamullah.feature_quran.data

import com.chibatching.kotpref.KotprefModel

object SettingPreference : KotprefModel() {

    private const val INDONESIA = 0
    private const val ENGLISH = 1

    var currentQori by intPref(0)
    var currentLang by intPref(INDONESIA)

    data class Qori(
        val qoriName: String,
        val qoriId: String
    )

    val listQori = listOf(
        Qori(
            qoriName = "Abdul Basit Abdul Samad",
            qoriId = "AbdulSamad_64kbps_QuranExplorer.Com"
        ),
        Qori(
            qoriName = "Alafasy",
            qoriId = "Alafasy_64kbps"
        )
    )
}
