package com.malbyte.qurankalamullah.feature_quran.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.malbyte.qurankalamullah.feature_quran.domain.model.Bookmark

@Database(version = 2, entities = [Bookmark::class])
abstract class BookmarkDatabase: RoomDatabase() {
    abstract fun getBookmarkDao(): BookmarkDao
}