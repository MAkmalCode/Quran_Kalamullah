package com.malbyte.qurankalamullah.feature_quran.domain.use_case

import com.malbyte.qurankalamullah.feature_quran.domain.model.Bookmark
import com.malbyte.qurankalamullah.feature_quran.domain.repository.QuranRepository
import kotlinx.coroutines.flow.Flow

class GetBookmarkUseCase(
    val quranRepository: QuranRepository
) {

    operator fun invoke(): Flow<List<Bookmark>>{
        return quranRepository.getBookmarkList()
    }
}