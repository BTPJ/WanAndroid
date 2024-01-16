package com.btpj.wanandroid.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.utils.LogUtil
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

    if (collectData != null) {
        LogUtil.d("LTP",collectData.toString())
        LogUtil.d("LTP",uiState?.data.toString())
        uiState?.data?.forEach {
            if (it.id == collectData!!.id) {
                it.collect = collectData!!.collect
            }
        }
        LogUtil.d("LTP",uiState?.data.toString())
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
