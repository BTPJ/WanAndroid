package com.btpj.wanandroid.ui.main.square

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.ui.main.ProvideViewModels
import com.btpj.wanandroid.ui.main.project.ProjectChildPage
import com.btpj.wanandroid.ui.main.square.ask.AskPage
import com.btpj.wanandroid.ui.main.square.square.SquareChildPage
import kotlinx.coroutines.launch

/**
 * 广场Tab
 * @author LTP  2023/12/14
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SquarePage(onArticleClick: (Article) -> Unit) {
    val titleList = listOf("广场", "每日一问", "体系", "导航")

    val pagerState = rememberPagerState { titleList.size }
    val coroutineScope = rememberCoroutineScope()

    Column {
        Box {
            TitleBar(title = "", showBackBtn = false)
            ScrollableTabRow(containerColor = MaterialTheme.colorScheme.primary,
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 20.dp,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = Color.White
                    )
                }) {
                titleList.forEachIndexed { index, title ->
                    Tab(modifier = Modifier
                        .height(52.dp)
                        .padding(horizontal = 10.dp),
                        selected = pagerState.currentPage == index,
                        unselectedContentColor = Color.Red,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = index)
                            }
                        }) {
                        Text(text = title, color = Color.White)
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
                when (it) {
                    0 -> SquareChildPage(onArticleClick = onArticleClick)
                    1 -> AskPage(onArticleClick = onArticleClick)
                }
            }
        }
    }
}