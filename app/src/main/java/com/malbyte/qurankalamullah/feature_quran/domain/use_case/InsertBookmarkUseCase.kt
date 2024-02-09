package com.malbyte.qurankalamullah.feature_quran.domain.use_case

import com.malbyte.qurankalamullah.feature_quran.domain.model.Bookmark
import com.malbyte.qurankalamullah.feature_quran.domain.repository.QuranRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class InsertBookmarkUseCase(
    private val quranRepository: QuranRepository
) {

    suspend operator fun invoke(bookmark: Bookmark){
        return  quranRepository.insertBookmark(bookmark)
    }
}