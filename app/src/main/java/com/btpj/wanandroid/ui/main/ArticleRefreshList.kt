package com.btpj.wanandroid.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.btpj.lib_base.ui.widgets.RefreshList
import com.btpj.wanandroid.App
import com.btpj.wanandroid.data.bean.Article

/**
 * @author LTP  2023/12/22
 */
@Composable
fun ArticleRefreshList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: ArticleViewModel,
    lazyListState: LazyListState = rememberLazyListState(),
    onRefresh: (() -> Unit)?,
    onLoadMore: (() -> Unit)? = null,
    headerContent: @Composable (() -> Unit)? = null,
    itemContent: @Composable (Article) -> Unit
) {
    val uiState by viewModel.uiState.observeAsState()
    val collectData by App.appViewModel.collectEvent.observeAsState()
    val user by App.appViewModel.user.collectAsState()
    val unCollectEvent by viewModel.unCollectEvent.observeAsState()

    // 收藏事件监听
    if (collectData != null) {
        uiState?.data?.forEach {
            if (it.id == collectData!!.id) {
                it.collect = collectData!!.collect
            }
        }
    }

    // 我收藏的文章列表中取消收藏
    if (unCollectEvent != null) {
        uiState?.data = uiState?.data?.filter {
            it.id != unCollectEvent
        }
    }

    // 用户退出时，收藏应全为false，登录时获取collectIds
    if (user == null) {
        uiState?.data?.forEach {
            it.collect = false
        }
    } else {
        uiState?.data?.forEach {
            user!!.collectIds.forEach { id ->
                if (id == it.id) {
                    it.collect = true
                }
            }
        }
    }

    RefreshList(
        modifier = modifier,
        contentPadding = contentPadding,
        uiState = uiState,
        lazyListState = lazyListState,
        onRefresh = onRefresh,
        onLoadMore = onLoadMore,
        headerContent = headerContent,
        itemContent = itemContent
    )
}
