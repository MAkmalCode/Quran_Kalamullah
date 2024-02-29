package com.malbyte.qurankalamullah.feature_quran.domain.use_case

import com.malbyte.qurankalamullah.feature_quran.domain.model.Surah
import com.malbyte.qurankalamullah.feature_quran.domain.repository.QuranRepository
import kotlinx.coroutines.flow.Flow

class SearchSurahUseCase(
    private val quranRepository: QuranRepository
) {

    operator fun invoke(search: String): Flow<List<Surah>> {
        return quranRepository.searchSurah(search)
    }
}