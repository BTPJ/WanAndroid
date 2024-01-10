package com.btpj.wanandroid.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.BaseViewModel
import kotlinx.coroutines.delay

/**
 * @author LTP  2023/12/22
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T : ProvideItemKey> RefreshList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: BaseViewModel<List<T>>,
    lazyListState: LazyListState,
    onRefresh: () -> Unit,
    onLoadMore: (() -> Unit)? = null,
    headerContent: @Composable (() -> Unit)? = null,
    itemContent: @Composable (T) -> Unit
) {
    val uiState by viewModel.uiState.observeAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState?.showLoading ?: false,
        onRefresh = onRefresh
    )
    LaunchedEffect(true) {
        if (uiState?.data == null) {
            onRefresh()
        }
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            state = lazyListState,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                headerContent?.invoke()
            }
            uiState?.data?.let {
                items(it, key = { item -> item.provideKey() }) { t ->
                    itemContent(t)
                }
                if (onLoadMore != null) {
                    item {
                        if (uiState?.showLoadingMore == true) {
                            LoadingView()
                            LaunchedEffect(Unit) {
                                delay(500)
                                onLoadMore()
                            }
                        }
                    }
                    item {
                        if (uiState?.noMoreData == true) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 20.dp),
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
        }

        PullRefreshIndicator(
            refreshing = uiState?.showLoading ?: false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(30.dp))
    }
}

interface ProvideItemKey {
    fun provideKey(): Int
}