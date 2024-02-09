package com.malbyte.qurankalamullah.feature_quran.domain.use_case

import com.malbyte.qurankalamullah.feature_quran.domain.model.Juz
import com.malbyte.qurankalamullah.feature_quran.domain.repository.QuranRepository
import kotlinx.coroutines.flow.Flow

class GetJuzUseCase(
    private val repository: QuranRepository
) {
    operator fun invoke(): Flow<List<Juz>> {
        return repository.getJuz()
    }
}