package com.malbyte.qurankalamullah.feature_quran.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Surah(
    @ColumnInfo("sora")
    val surah: Int?,
    @ColumnInfo("sora_name_en")
    val surahNameEn: String?,
    @ColumnInfo("sora_name_ar")
    val surahNameAr: String?,
    @ColumnInfo(name = "total_ayah")
    val totalAyah: Int?,
    @ColumnInfo("sora_descend_place")
    val surahDescendPlace: String?
)