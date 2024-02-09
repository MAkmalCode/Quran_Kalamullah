package com.malbyte.qurankalamullah.feature_quran.di

import android.app.Application
import com.malbyte.qurankalamullah.feature_quran.data.data_source.QuranDatabase
import com.malbyte.qurankalamullah.feature_quran.data.repository.QuranRepositoryImpl
import com.malbyte.qurankalamullah.feature_quran.domain.repository.QuranRepository
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.GetJuzUseCase
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.GetSurahUseCase
import com.malbyte.qurankalamullah.feature_quran.domain.use_case.QuranUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuranDatabase(app: Application): QuranDatabase {
        return QuranDatabase.getInstance(app)
    }

    @Provides
    @Singleton
    fun provideQuranRepository(db: QuranDatabase): QuranRepository {
        return QuranRepositoryImpl(db.quranDao)
    }

    @Provides
    @Singleton
    fun provideQuranUseCase(repository: QuranRepository): QuranUseCases {
        return QuranUseCases(
            getSurahUseCase = GetSurahUseCase(repository),
            getJuzUseCase = GetJuzUseCase(repository)
        )
    }
}