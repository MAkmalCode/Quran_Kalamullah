package com.malbyte.qurankalamullah.feature_quran.domain.use_case

import com.malbyte.qurankalamullah.feature_quran.domain.repository.QuranRepository

class DeleteAllBookmark(
    val quranRepository: QuranRepository
) {

    suspend operator fun invoke(){
        return quranRepository.deleteAllBookmark()
    }
}