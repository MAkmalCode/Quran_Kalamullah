package com.malbyte.qurankalamullah.feature_quran.domain.use_case

import com.malbyte.qurankalamullah.feature_quran.domain.model.Surah
import com.malbyte.qurankalamullah.feature_quran.domain.repository.QuranRepository
import kotlinx.coroutines.flow.Flow

class GetSurahUseCase(
    private val repository: QuranRepository
) {

    operator fun invoke(): Flow<List<Surah>> {
        return repository.getSurah()
    }

}