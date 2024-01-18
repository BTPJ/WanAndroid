package com.btpj.wanandroid.ui.main.square

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.Structure
import com.btpj.wanandroid.navigation.Route
import com.btpj.wanandroid.ui.main.square.ask.AskPage
import com.btpj.wanandroid.ui.main.square.navigation.NavigationPage
import com.btpj.wanandroid.ui.main.square.square.SquareChildPage
import com.btpj.wanandroid.ui.main.square.system.SystemPage
import kotlinx.coroutines.launch

/**
 * 广场Tab
 * @author LTP  2023/12/14
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SquarePage(
    navHostController: NavHostController,
    onStructureClick: ((Structure, Int) -> Unit)? = null,
    onNavigationClick: ((Article) -> Unit)? = null,
    onArticleClick: (Article) -> Unit
) {
    val titleList = remember {
        listOf("广场", "每日一问", "体系", "导航")
    }

    val pagerState = rememberPagerState { titleList.size }
    val coroutineScope = rememberCoroutineScope()

    val lazyListStates = (1..titleList.size).map { rememberLazyListState() }

    Column {
        Box {
            TitleBar(menu = {
                if (pagerState.currentPage == 0) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.clickable { navHostController.navigate(Route.ADD_ARTICLE) })
                }
            })
            ScrollableTabRow(
                modifier = Modifier.padding(horizontal = 40.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 10.dp,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }) {
                titleList.forEachIndexed { index, title ->
                    Tab(modifier = Modifier
                        .height(52.dp)
                        .padding(horizontal = 10.dp),
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = index)
                            }
                        }) {
                        Text(text = title, color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), contentAlignment = Alignment.TopCenter
        ) {
            HorizontalPager(modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.Top,
                state = pagerState,
                key = { index -> index }) {
                if (it == pagerState.currentPage) {
                    when (it) {
                        0 -> SquareChildPage(
                            lazyListState = lazyListStates[0],
                            onArticleClick = onArticleClick
                        )

                        1 -> AskPage(
                            lazyListState = lazyListStates[1],
                            onArticleClick = onArticleClick
                        )

                        2 -> SystemPage(
                            lazyListState = lazyListStates[2],
                            onStructureClick = { structure, pageIndex ->
                                onStructureClick?.invoke(structure, pageIndex)
                            })

                        3 -> NavigationPage(
                            lazyListState = lazyListStates[3],
                            onNavigationClick = { article ->
                                onNavigationClick?.invoke(article)
                            })
                    }
                }
            }
        }
    }
}