package com.malbyte.qurankalamullah.feature_quran.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.malbyte.qurankalamullah.feature_quran.domain.model.Juz
import com.malbyte.qurankalamullah.feature_quran.domain.model.Quran
import com.malbyte.qurankalamullah.feature_quran.domain.model.Surah
import kotlinx.coroutines.flow.Flow

@Dao
interface QuranDao {

    @Query("SELECT sora_name_en, sora_name_ar, sora_descend_place, sora, count(aya_no) AS total_ayah FROM quran GROUP by sora")
    fun getSurah(): Flow<List<Surah>>

    @Query("SELECT sora, aya_no, jozz, sora_name_en, sora_name_ar, sora_descend_place FROM quran GROUP BY sora, jozz")
    fun getJuz(): Flow<List<Juz>>

    @Query("SELECT * FROM quran WHERE sora = :sora")
    fun getReadSurah(sora: Int): Flow<List<Quran>>

    @Query("SELECT * FROM quran WHERE jozz = :juz")
    fun getReadJuz(juz: Int): Flow<List<Quran>>

    @Query("SELECT * FROM quran where sora_name_emlaey LIKE '%'||:search||'%' OR sora = '%'||:search||'%' GROUP BY sora")
    fun searchSurah(search: String): Flow<List<Surah>>

    @Query("SELECT * FROM quran where translation_id LIKE '%'||:search||'%' OR aya_text_emlaey = '%'||:search||'%' OR translation_en = '%'||:search||'%' GROUP BY sora")
    fun searchEntireQuran(search: String): Flow<List<Quran>>
}