package com.malbyte.qurankalamullah.feature_quran.domain.repository

import com.malbyte.qurankalamullah.feature_quran.domain.model.Juz
import com.malbyte.qurankalamullah.feature_quran.domain.model.Surah
import kotlinx.coroutines.flow.Flow

interface QuranRepository {

    fun getSurah(): Flow<List<Surah>>

    fun getJuz(): Flow<List<Juz>>
}