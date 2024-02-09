package com.malbyte.qurankalamullah.feature_quran.domain.use_case

data class QuranUseCases(
    val getSurahUseCase: GetSurahUseCase,
    val getJuzUseCase: GetJuzUseCase,
    val getReadSurahUsecase: GetReadSurahUsecase,
    val getReadJuzUseCase: GetReadJuzUseCase,
    val insertBookmarkUseCase: InsertBookmarkUseCase,
    val getBookmarkUseCase: GetBookmarkUseCase,
    val deleteAllBookmark: DeleteAllBookmark,
    val deleteBookmark: DeleteBookmark
)
