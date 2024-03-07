package com.malbyte.qurankalamullah.presentation.on_boarding_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malbyte.qurankalamullah.feature_quran.data.GlobalPreference
import com.malbyte.qurankalamullah.feature_quran.data.SettingPreference
import com.malbyte.qurankalamullah.presentation.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalFoundationApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun OnBoardingScreen(
    navigator: DestinationsNavigator
) {

    if (GlobalPreference.firstTime == 1){
        navigator.clearBackStack(HomeScreenDestination)
    }

    val langState by remember {
        mutableStateOf(SettingPreference.currentLang)
    }

    val contentList = listOf(
        onBoardingContent(
            image = 0,
            title = "Language / Bahasa",
            subTitle = "Click the button / Pencet Tombolnya"
        ),
        onBoardingContent(
            image = 9,
            title = if (langState == SettingPreference.INDONESIA) "Selamat Datang Di Qur'an Kalamullah" else "Welcome to Qur'an Kalmullah",
            subTitle = if (langState == SettingPreference.INDONESIA) "Slide Ke Kanan Untuk Mempelajari Lebih Lanjut" else "Slide Right To Learn More"
        ),
        onBoardingContent(
            image = 8,
            title = if (langState == SettingPreference.INDONESIA) "Buat Ini Menjadi Mudah" else "Make It Easy",
            subTitle = if (langState == SettingPreference.INDONESIA)
                "Fitur-fitur yang tersedia:" +
                        "   \n- Pilihan Qori" +
                        "   \n- 2 Pilihan Bahasa" +
                        "   \n- 2 Mencari Qiblat" +
                        "   \n- 2 Jadwal Adzan"
            else
                "Available features:" +
                        "   \n- Qori Choice" +
                        "   \n- 2 Language Options" +
                        "   \n- 2 Looking for Qibla" +
                        "   \n- 2 A.dhan Schedules",
        ),
        onBoardingContent(
            image = 9,
            title = if (langState == SettingPreference.INDONESIA) "Mari kita mulai" else "Let's Get Started",
            subTitle = ""
        )
    )

    val pagerState = rememberPagerState {
        contentList.size
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            state = pagerState,
            Modifier.wrapContentSize()
        ) { currentPage ->
            Column(
                Modifier
                    .wrapContentSize()
                    .padding(26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = contentList[currentPage].title,
                    textAlign = TextAlign.Center,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = contentList[currentPage].subTitle,
                    Modifier.padding(top = 45.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    }
}

data class onBoardingContent(
    val image: Int,
    val title: String,
    val subTitle: String
)