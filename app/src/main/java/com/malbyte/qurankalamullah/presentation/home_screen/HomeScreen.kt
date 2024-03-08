package com.malbyte.qurankalamullah.presentation.home_screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.malbyte.qurankalamullah.feature_quran.data.LastRead
import com.malbyte.qurankalamullah.feature_quran.domain.model.Bookmark
import com.malbyte.qurankalamullah.feature_quran.domain.utils.mapToJuzIndexing
import com.malbyte.qurankalamullah.presentation.GlobalViewModel
import com.malbyte.qurankalamullah.presentation.home_screen.components.BookmarkItems
import com.malbyte.qurankalamullah.presentation.destinations.ReadScreenDestination
import com.malbyte.qurankalamullah.presentation.home_screen.components.JuzItem
import com.malbyte.qurankalamullah.presentation.home_screen.components.SurahItem
import com.malbyte.qurankalamullah.presentation.read_screen.ReadArg
import com.malbyte.qurankalamullah.ui.component.HomeItem
import com.malbyte.qurankalamullah.ui.theme.Primary
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.compose.runtime.collectAsState
import com.malbyte.qurankalamullah.feature_quran.data.GlobalPreference
import com.malbyte.qurankalamullah.feature_quran.data.SettingPreference
import com.malbyte.qurankalamullah.presentation.destinations.FindQiblaScreenDestination
import com.malbyte.qurankalamullah.presentation.destinations.SettingScreenDestination

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Destination
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    globalViewModel: GlobalViewModel,
    navigator: DestinationsNavigator
) {

    val context = LocalContext.current
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        tabListitems.size
    }
    val surahState = homeViewModel.surahState.value
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }
    LaunchedEffect(surahState) {
        globalViewModel.setTotalAyah(surahList = surahState.listSurah)
    }

    val juzState by homeViewModel.juzState.collectAsStateWithLifecycle()
    val searchText by homeViewModel.searchText.collectAsState()
    val isSearching by homeViewModel.isSearching.collectAsState()
    val quranList by homeViewModel.searchSurahState.collectAsState()

    LaunchedEffect(searchText) {
        homeViewModel.searchResult(searchText)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Qur'an Kalamullah")
                },
                actions = {
                    IconButton(onClick = {
                        navigator.navigate(FindQiblaScreenDestination)
                    }) {
                        Icon(imageVector = Icons.Rounded.Explore, contentDescription = "")
                    }
                    IconButton(onClick = {
                        navigator.navigate(SettingScreenDestination)
                    }) {
                        Icon(imageVector = Icons.Rounded.Settings, contentDescription = "")
                    }
                }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {

            SearchBar(
                query = searchText,
                onQueryChange = {
                    homeViewModel.onSearchTextChange(it)
                },
                onSearch = {
                    homeViewModel.onSearchTextChange(it)
                },
                active = isSearching,
                onActiveChange = { homeViewModel.onToogleSearch() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                leadingIcon = {
                    IconButton(onClick = {
                        navigator.navigateUp()
                    }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
                    }
                }
            ) {
                LazyColumn(content = {
                    items(quranList.listQuran) {
                        SurahItem(
                            surahNo = it.surah,
                            surahNameEn = it.surahNameEn,
                            surahNameAr = it.surahNameAr,
                            totalAyah = it.totalAyah,
                            surahDescendPlace = it.surahDescendPlace
                        ) {
                            navigator.navigate(
                                ReadScreenDestination(
                                    ReadArg(
                                        readType = 0,
                                        surahNumb = it.surah,
                                        juzNumb = null,
                                        position = 0
                                    )
                                )
                            )
                        }
                    }
                })
            }

            HomeItem(
                item = if (SettingPreference.currentLang == SettingPreference.INDONESIA) "Terakhir di baca" else "Last read",
                surah = LastRead.surahName,
                ayah = LastRead.ayahNumber
            ) {
                navigator.navigate(
                    ReadScreenDestination(
                        ReadArg(
                            2,
                            LastRead.surahNumber,
                            0,
                            LastRead.position
                        )
                    )
                )
            }

            TabRow(
                selectedTabIndex = selectedTabIndex,
                backgroundColor = Color.White,
                contentColor = Primary,
                modifier = Modifier
                    .padding(16.dp)
                    .clip(CircleShape),
            ) {
                tabListitems.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = {
                            selectedTabIndex = index
                        },
                        text = {
                            Text(
                                text = item,
                                color = Primary
                            )
                        }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index ->
                when (index) {

                    0 -> {
                        LazyColumn(content = {
                            items(surahState.listSurah) { surah ->
                                SurahItem(
                                    surahNo = surah.surah,
                                    surahNameEn = surah.surahNameEn,
                                    surahNameAr = surah.surahNameAr,
                                    totalAyah = surah.totalAyah,
                                    surahDescendPlace = surah.surahDescendPlace,
                                    goToRead = {
                                        navigator.navigate(
                                            ReadScreenDestination(
                                                navArgs = ReadArg(
                                                    0,
                                                    surah.surah,
                                                    0,
                                                    0
                                                )
                                            )
                                        )
                                    }
                                )
                            }
                        })
                    }

                    1 -> {
                        LazyColumn(content = {
                            val groupByJuz = juzState.listJuz.mapToJuzIndexing()
                            items(groupByJuz) { juz ->
                                JuzItem(
                                    juzNumb = juz.juzNumber,
                                    listSurahJuz = juz.surahList,
                                    surahNumblist = juz.surahNumberList
                                ) {
                                    navigator.navigate(
                                        ReadScreenDestination(
                                            navArgs = ReadArg(
                                                1,
                                                0,
                                                juz.juzNumber,
                                                0
                                            )
                                        )
                                    )
                                }
                            }
                        })
                    }

                    2 -> {
                        val bookmarkList =
                            homeViewModel.bookmarkState.collectAsStateWithLifecycle().value

                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {

                            Text(
                                text = "Delete All",
                                color = Primary,
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .padding(end = 16.dp)
                                    .clickable {
                                        homeViewModel.deleteAllBookmark()
                                        Toast
                                            .makeText(context, "Delete All", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            LazyColumn(content = {
                                items(bookmarkList.size) { index ->
                                    val bookmark = bookmarkList[index]
                                    BookmarkItems(
                                        surahName = bookmark.surahName,
                                        ayahNumber = bookmark.ayahNumber,
                                        previewText = bookmark.ayahText,
                                        timeAdded = bookmark.timeAdded,
                                        goToRead = {
                                            navigator.navigate(
                                                ReadScreenDestination(
                                                    ReadArg(
                                                        2,
                                                        bookmark.surahNumber,
                                                        0,
                                                        bookmark.position
                                                    )
                                                )
                                            )
                                        },
                                        delete = {
                                            homeViewModel.deleteBookmmark(
                                                Bookmark(
                                                    bookmark.id,
                                                    bookmark.surahName,
                                                    bookmark.ayahNumber,
                                                    bookmark.ayahNumber,
                                                    bookmark.ayahText,
                                                    bookmark.position,
                                                    bookmark.timeAdded
                                                )
                                            )
                                            Toast.makeText(context, "Delete", Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    )
                                }
                            })
                        }
                    }
                }
            }
        }
    }
}