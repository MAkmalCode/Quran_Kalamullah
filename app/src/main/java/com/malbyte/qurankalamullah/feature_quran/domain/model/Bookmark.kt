package com.malbyte.qurankalamullah.feature_quran.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class Bookmark(
    @PrimaryKey val id: Int,
    val surahName: String,
    val ayahNumber: Int,
    val surahNumber: Int,
    val ayahText: String,
    val position: Int,
    val timeAdded: Long
)