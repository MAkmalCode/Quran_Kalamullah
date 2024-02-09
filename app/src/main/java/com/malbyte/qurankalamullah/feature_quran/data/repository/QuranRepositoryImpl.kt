package com.malbyte.qurankalamullah.feature_quran.data.repository

import com.malbyte.qurankalamullah.feature_quran.data.data_source.QuranDao
import com.malbyte.qurankalamullah.feature_quran.domain.model.Juz
import com.malbyte.qurankalamullah.feature_quran.domain.model.Surah
import com.malbyte.qurankalamullah.feature_quran.domain.repository.QuranRepository
import kotlinx.coroutines.flow.Flow

class QuranRepositoryImpl(
    private val dao: QuranDao
) : QuranRepository {

    override fun getSurah(): Flow<List<Surah>> {
        return dao.getSurah()
    }

    override fun getJuz(): Flow<List<Juz>> {
        return dao.getJuz()
    }
}