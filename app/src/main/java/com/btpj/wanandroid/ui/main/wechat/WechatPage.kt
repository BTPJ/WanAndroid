package com.btpj.wanandroid.ui.main.wechat

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.btpj.lib_base.ext.toHtml
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.data.bean.Article
import kotlinx.coroutines.launch

/**
 * @author LTP  2023/12/21
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WechatPage(
    wechatViewModel: WechatViewModel = viewModel(),
    onArticleClick: (Article) -> Unit
) {

    val authorTitleList by wechatViewModel.authorTitleListLiveData.observeAsState()
    val lazyListStates =
        if (authorTitleList.isNullOrEmpty()) emptyList() else (1..authorTitleList!!.size).map { rememberLazyListState() }

    val pagerState = rememberPagerState { authorTitleList?.size ?: 0 }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        wechatViewModel.fetchAuthorTitleList()
    }

    Column {
        Box {
            TitleBar()
            if (!authorTitleList.isNullOrEmpty()) {
                ScrollableTabRow(
                    containerColor = MaterialTheme.colorScheme.primary,
                    selectedTabIndex = pagerState.currentPage,
                    edgePadding = 20.dp,
                    divider = {},
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = Color.White
                        )
                    }
                ) {
                    authorTitleList?.forEachIndexed { index, title ->
                        Tab(
                            modifier = Modifier
                                .height(52.dp)
                                .padding(horizontal = 10.dp),
                            selected = pagerState.currentPage == index,
                            unselectedContentColor = Color.Red,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(page = index)
                                }
                            }) {
                            Text(text = title.name.toHtml().toString(), color = Color.White)
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = if (authorTitleList.isNullOrEmpty()) Alignment.Center else Alignment.TopCenter
        ) {
            if (authorTitleList.isNullOrEmpty()) {
                CircularProgressIndicator()
            } else {
                HorizontalPager(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.Top,
                    state = pagerState,
                    key = { index -> index }
                ) {
                    // 这个回调很闹眼子
                    if (it == pagerState.currentPage) {
                        WechatChildPage(
                            authorId = authorTitleList!![it].id,
                            lazyListState = lazyListStates[it],
                            onArticleClick = onArticleClick
                        )
                    }
                }
            }
        }
    }
}

