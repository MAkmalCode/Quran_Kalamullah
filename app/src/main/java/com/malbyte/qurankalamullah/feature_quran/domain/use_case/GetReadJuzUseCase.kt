package com.malbyte.qurankalamullah.feature_quran.domain.use_case

import com.malbyte.qurankalamullah.feature_quran.domain.model.Juz
import com.malbyte.qurankalamullah.feature_quran.domain.model.Quran
import com.malbyte.qurankalamullah.feature_quran.domain.repository.QuranRepository
import kotlinx.coroutines.flow.Flow

class GetReadJuzUseCase(
    private val repository: QuranRepository
) {

    operator fun invoke(juz: Int): Flow<List<Quran>> {
        return repository.getReadJuz(juz)
    }
}