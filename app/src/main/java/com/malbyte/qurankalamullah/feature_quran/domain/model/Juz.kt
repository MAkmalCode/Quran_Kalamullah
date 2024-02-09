package com.malbyte.qurankalamullah.feature_quran.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Juz(
    @ColumnInfo("sora")
    val surah: Int?,
    @ColumnInfo("aya_no")
    val ayahNumb: Int?,
    @ColumnInfo("jozz")
    val juz: Int?,
    @ColumnInfo("sora_name_en")
    val surahNameEng: String?,
    @ColumnInfo("sora_name_ar")
    val surahNameAr: String?,
    @ColumnInfo("sora_descend_place")
    val descendPlace: String?,
)