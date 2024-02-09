package com.malbyte.qurankalamullah.feature_quran.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.malbyte.qurankalamullah.R
import com.malbyte.qurankalamullah.feature_quran.domain.model.Quran

@Database(version = 1, entities = [Quran::class])
abstract class QuranDatabase : RoomDatabase() {
    abstract val quranDao: QuranDao

    companion object {
        @Volatile
        private var INSTANCE: QuranDatabase? = null
        fun getInstance(context: Context): QuranDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    QuranDatabase::class.java,
                    "qoran.db"
                ).createFromInputStream {
                    context.resources.openRawResource(R.raw.qoran)
                }.build()
            }
        }
    }
}