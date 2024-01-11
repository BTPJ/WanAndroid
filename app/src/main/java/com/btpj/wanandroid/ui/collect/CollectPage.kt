package com.btpj.wanandroid.ui.collect

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.CollectUrl
import com.btpj.wanandroid.ui.collect.article.CollectArticlePage
import com.btpj.wanandroid.ui.collect.url.CollectUrlPage
import kotlinx.coroutines.launch

/**
 * 广场Tab
 * @author LTP  2023/12/14
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollectPage(
    navHostController: NavHostController,
    onCollectUrlClick: (CollectUrl) -> Unit = {},
    onArticleClick: (Article) -> Unit = {}
) {
    val titleList = remember {
        listOf("文章", "网址")
    }

    val pagerState = rememberPagerState { titleList.size }
    val coroutineScope = rememberCoroutineScope()

    val lazyListStates = (1..titleList.size).map { rememberLazyListState() }

    Column {
        Box {
            TitleBar { navHostController.popBackStack() }
            TabRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 100.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                selectedTabIndex = pagerState.currentPage,
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
                if (it == pagerState.currentPage) {
                    when (it) {
                        0 -> CollectArticlePage(
                            lazyListState = lazyListStates[0],
                            onArticleClick = onArticleClick
                        )

                        1 -> CollectUrlPage(
                            lazyListState = lazyListStates[1],
                            onUrlClick = onCollectUrlClick
                        )
                    }
                }
            }
        }
    }
}