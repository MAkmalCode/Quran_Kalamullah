package com.malbyte.qurankalamullah.feature_quran.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.malbyte.qurankalamullah.feature_quran.data.data_source.BookmarkDatabase
import com.malbyte.qurankalamullah.feature_quran.data.data_source.QuranDatabase
import com.malbyte.qurankalamullah.feature_quran.data.repository.QuranRepositoryImpl
import com.malbyte.qurankalamullah.feature_quran.domain.repository.QuranRepository
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.DeleteAllBookmark
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.DeleteBookmark
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.GetBookmarkUseCase
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.GetJuzUseCase
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.GetReadJuzUseCase
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.GetReadSurahUsecase
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.GetSurahUseCase
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.InsertBookmarkUseCase
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.QuranUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookmarkDatabase(
        @ApplicationContext context: Context
    ): BookmarkDatabase{
        return Room.databaseBuilder(
            context,
            BookmarkDatabase::class.java,
            "bookmark.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideQuranDatabase(app: Application): QuranDatabase {
        return QuranDatabase.getInstance(app)
    }

    @Provides
    @Singleton
    fun provideQuranRepository(quranDatabase: QuranDatabase, bookmarkDatabase: BookmarkDatabase): QuranRepository {
        return QuranRepositoryImpl(quranDatabase.quranDao, bookmarkDatabase.getBookmarkDao())
    }

    @Provides
    @Singleton
    fun provideQuranUseCase(repository: QuranRepository): QuranUseCases {
        return QuranUseCases(
            getSurahUseCase = GetSurahUseCase(repository),
            getJuzUseCase = GetJuzUseCase(repository),
            getReadSurahUsecase = GetReadSurahUsecase(repository),
            getReadJuzUseCase = GetReadJuzUseCase(repository),
            insertBookmarkUseCase = InsertBookmarkUseCase(repository),
            getBookmarkUseCase = GetBookmarkUseCase(repository),
            deleteAllBookmark = DeleteAllBookmark(repository),
            deleteBookmark = DeleteBookmark(repository)
        )
    }
}