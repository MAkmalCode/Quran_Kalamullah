package com.malbyte.qurankalamullah.feature_quran.data.repository

import com.malbyte.qurankalamullah.feature_quran.data.data_source.local.BookmarkDao
import com.malbyte.qurankalamullah.feature_quran.data.data_source.local.QuranDao
import com.malbyte.qurankalamullah.feature_quran.data.data_source.remote.AdzanScheduleResponse
import com.malbyte.qurankalamullah.feature_quran.data.data_source.remote.ApiInterface
import com.malbyte.qurankalamullah.feature_quran.domain.model.Bookmark
import com.malbyte.qurankalamullah.feature_quran.domain.model.Juz
import com.malbyte.qurankalamullah.feature_quran.domain.model.Quran
import com.malbyte.qurankalamullah.feature_quran.domain.model.Surah
import com.malbyte.qurankalamullah.feature_quran.domain.repository.QuranRepository
import kotlinx.coroutines.flow.Flow

class QuranRepositoryImpl (
    private val quranDao: QuranDao,
    private val bookmarkDao: BookmarkDao,
    private val api: ApiInterface
) : QuranRepository {

    override fun getSurah(): Flow<List<Surah>> {
        return quranDao.getSurah()
    }

    override fun getJuz(): Flow<List<Juz>> {
        return quranDao.getJuz()
    }

    override fun getReadSurah(sora: Int): Flow<List<Quran>> {
        return quranDao.getReadSurah(sora)
    }

    override fun getReadJuz(juz: Int): Flow<List<Quran>> {
        return quranDao.getReadJuz(juz)
    }

    override fun getBookmarkList(): Flow<List<Bookmark>> {
        return bookmarkDao.getBookmarkList()
    }

    override suspend fun insertBookmark(bookmark: Bookmark) {
        return bookmarkDao.insertBookmark(bookmark)
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        return bookmarkDao.deleteBookmark(bookmark)
    }

    override suspend fun deleteAllBookmark() {
        return bookmarkDao.deleteAllBookmark()
    }

    override fun searchSurah(search: String): Flow<List<Surah>> {
        return quranDao.searchSurah(search)
    }


    override fun searchEntireQuran(search: String): Flow<List<Quran>> {
        return  quranDao.searchEntireQuran(search)
    }

    override suspend fun getAdzanSchedule(
        latitude: String,
        longitude: String
    ): AdzanScheduleResponse {
        return api.getAdzanSchedule(latitude, longitude)
    }
}