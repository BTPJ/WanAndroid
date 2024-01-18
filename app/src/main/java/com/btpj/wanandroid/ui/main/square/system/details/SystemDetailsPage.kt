package com.btpj.wanandroid.ui.main.square.system.details

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.Structure
import kotlinx.coroutines.launch

/**
 * 首页Tab
 * @author LTP  2023/12/14
 */
@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SystemDetailsPage(
    navHostController: NavHostController,
    structure: Structure,
    pageIndex: Int,
    onArticleClick: (Article) -> Unit
) {
    val titleList = structure.children.map { it.name }
    val lazyListStates = (1..titleList.size).map { rememberLazyListState() }

    val pagerState = rememberPagerState(pageIndex) { structure.children.size }
    val coroutineScope = rememberCoroutineScope()

    Column {
        TitleBar(title = structure.name) { navHostController.popBackStack() }
        ScrollableTabRow(
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

        HorizontalPager(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.Top,
            state = pagerState,
            key = { index -> index }
        ) {
            if (it == pagerState.currentPage) {
                SystemDetailsChildPage(
                    lazyListState = lazyListStates[it],
                    categoryId = structure.children[it].id,
                    onArticleClick = onArticleClick
                )
            }
        }
    }
}
