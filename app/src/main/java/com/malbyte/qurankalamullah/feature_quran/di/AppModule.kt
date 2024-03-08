package com.malbyte.qurankalamullah.feature_quran.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.android.gms.location.LocationServices
import com.malbyte.qurankalamullah.feature_quran.data.data_source.local.BookmarkDatabase
import com.malbyte.qurankalamullah.feature_quran.data.data_source.local.QuranDatabase
import com.malbyte.qurankalamullah.feature_quran.data.data_source.remote.ApiInterface
import com.malbyte.qurankalamullah.feature_quran.data.location.LocationClient
import com.malbyte.qurankalamullah.feature_quran.data.location.LocationClientImpl
import com.malbyte.qurankalamullah.feature_quran.data.repository.QuranRepositoryImpl
import com.malbyte.qurankalamullah.feature_quran.domain.repository.QuranRepository
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.DeleteAllBookmark
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.DeleteBookmark
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.GetBookmarkUseCase
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.GetJuzUseCase
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.GetPrayerScheduleUseCase
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.GetReadJuzUseCase
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.GetReadSurahUsecase
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.GetSurahUseCase
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.InsertBookmarkUseCase
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.QuranUseCases
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.SearchSurahUseCase
import com.malbyte.qurankalamullah.service.MyPlayerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import snow.player.PlayerClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocationClient(
        app: Application,
        coroutineScope: CoroutineScope
    ): LocationClient {
        return LocationClientImpl(
            app,
            LocationServices.getFusedLocationProviderClient(app.applicationContext),
            coroutineScope
        )
    }

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideApiConfig(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://prayer-times-xi.vercel.app/api/prayer/ ")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun providePlayer(
        @ApplicationContext context: Context
    ): PlayerClient{
        return PlayerClient.newInstance(context, MyPlayerService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookmarkDatabase(
        @ApplicationContext context: Context
    ): BookmarkDatabase {
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
    fun provideQuranRepository(quranDatabase: QuranDatabase, bookmarkDatabase: BookmarkDatabase, apiInterface: ApiInterface): QuranRepository {
        return QuranRepositoryImpl(quranDatabase.quranDao, bookmarkDatabase.getBookmarkDao(), apiInterface)
    }

    @Provides
    @Singleton
    fun provideQuranUseCase(repository: QuranRepository, apiInterface: ApiInterface): QuranUseCases {
        return QuranUseCases(
            getSurahUseCase = GetSurahUseCase(repository),
            getJuzUseCase = GetJuzUseCase(repository),
            getReadSurahUsecase = GetReadSurahUsecase(repository),
            getReadJuzUseCase = GetReadJuzUseCase(repository),
            insertBookmarkUseCase = InsertBookmarkUseCase(repository),
            getBookmarkUseCase = GetBookmarkUseCase(repository),
            deleteAllBookmark = DeleteAllBookmark(repository),
            deleteBookmark = DeleteBookmark(repository),
            searchSurahUseCase = SearchSurahUseCase(repository),
            getPrayerScheduleUseCase = GetPrayerScheduleUseCase(apiInterface)
        )
    }
}