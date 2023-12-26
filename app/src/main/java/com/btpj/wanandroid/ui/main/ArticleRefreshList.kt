package com.btpj.wanandroid.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.btpj.lib_base.utils.LogUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.ui.main.home.LoadingView
import kotlinx.coroutines.delay

/**
 * @author LTP  2023/12/22
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleRefreshList(
    articleViewModel: ArticleViewModel,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    itemContent: @Composable (Article) -> Unit
) {
    val articleUiState by articleViewModel.uiState.observeAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = articleUiState?.showLoading ?: false,
        onRefresh = onRefresh
    )
    LaunchedEffect(true) { onRefresh() }
    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            articleUiState?.list?.let {
                items(it, key = { item -> item.id }) { article ->
                    itemContent(article)
                }
                item {
                    if (articleUiState?.showLoadMoreLoading == true) {
                        LoadingView()
                        LaunchedEffect(Unit) {
                            delay(500)
                            onLoadMore()
                        }
                    }
                }
                item {
                    if (articleUiState?.noMoreData == true) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.noMoreData),
                                color = LocalContentColor.current.copy(alpha = 0.6f),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = articleUiState?.showLoading ?: false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}