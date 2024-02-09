package com.malbyte.qurankalamullah.feature_quran.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Types.INTEGER

@Entity(tableName = "quran")
data class Quran(
    @PrimaryKey
    val id: Int,
    @ColumnInfo("jozz")
    val juz: Int?,
    @ColumnInfo("sora")
    val surah: Int?,
    @ColumnInfo("sora_name_en")
    val surahNameEng: String?,
    @ColumnInfo("sora_name_ar")
    val surahNameAr: String?,
    val page: Int?,
    @ColumnInfo("aya_no")
    val ayahNumb: Int?,
    @ColumnInfo("aya_text")
    val ayahText: String?,
    @ColumnInfo("aya_text_emlaey")
    val ayahTextEmlaey: String?,
    @ColumnInfo("translation_id")
    val translationIndo: String?,
    @ColumnInfo("footnotes_id")
    val footnotesIndo: String?,
    @ColumnInfo("sora_name_id")
    val surahNameIndo: String?,
    @ColumnInfo("sora_descend_place")
    val descendPlace: String?,
    @ColumnInfo("sora_name_emlaey")
    val surahNameEmlaey: String?,
    @ColumnInfo("translation_en")
    val translationEng: String?,
    @ColumnInfo("footnotes_en")
    val footnotesEng: String?,
)