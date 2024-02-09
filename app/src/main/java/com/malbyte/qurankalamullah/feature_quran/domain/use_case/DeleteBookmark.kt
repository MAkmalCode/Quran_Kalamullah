package com.malbyte.qurankalamullah.feature_quran.domain.use_case

import com.malbyte.qurankalamullah.feature_quran.domain.model.Bookmark
import com.malbyte.qurankalamullah.feature_quran.domain.repository.QuranRepository

class DeleteBookmark(
    val quranRepository: QuranRepository
) {

    suspend operator fun invoke(bookmark: Bookmark){
        return quranRepository.deleteBookmark(bookmark)
    }

}