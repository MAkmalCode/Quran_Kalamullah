package com.malbyte.qurankalamullah.feature_quran.domain.utils

import com.malbyte.qurankalamullah.feature_quran.domain.model.Juz

fun List<Juz>.mapToJuzIndexing(): List<JuzWithSurahIndex> {
    val groupList = this.groupBy { it.juz }
    return groupList.map { (juzNumb, juzList) ->
        JuzWithSurahIndex(
            juzNumb,
            juzList.map { "${it.surahNameEng}: ${it.surah}" },
            juzList.map { it.surah }
        )
    }
}


data class JuzWithSurahIndex(
    val juzNumber: Int?,
    val surahList: List<String?>,
    val surahNumberList: List<Int?>
)
