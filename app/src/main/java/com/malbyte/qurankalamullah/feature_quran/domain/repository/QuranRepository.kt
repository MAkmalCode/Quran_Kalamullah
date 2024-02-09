package com.malbyte.qurankalamullah.feature_quran.domain.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.malbyte.qurankalamullah.feature_quran.domain.model.Bookmark
import com.malbyte.qurankalamullah.feature_quran.domain.model.Juz
import com.malbyte.qurankalamullah.feature_quran.domain.model.Quran
import com.malbyte.qurankalamullah.feature_quran.domain.model.Surah
import kotlinx.coroutines.flow.Flow

interface QuranRepository {

    fun getSurah(): Flow<List<Surah>>
    fun getJuz(): Flow<List<Juz>>
    fun getReadSurah(sora: Int): Flow<List<Quran>>
    fun getReadJuz(juz: Int): Flow<List<Quran>>
    fun getBookmarkList(): Flow<List<Bookmark>>
    suspend fun insertBookmark(bookmark: Bookmark)
    suspend fun deleteBookmark(bookmark: Bookmark)
    suspend fun deleteAllBookmark()
}