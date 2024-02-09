package com.malbyte.qurankalamullah.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.malbyte.qurankalamullah.feature_quran.domain.model.Surah
import javax.inject.Inject

class GlobalViewModel : ViewModel() {
    private val _totalAyah = mutableStateListOf<Int>()

    fun setTotalAyah(surahList: List<Surah>) {
        surahList.forEach {
            Log.d("check", it.totalAyah.toString())
            _totalAyah.add(it.totalAyah!!)
        }
    }

    fun getTotalAyah() = _totalAyah
}