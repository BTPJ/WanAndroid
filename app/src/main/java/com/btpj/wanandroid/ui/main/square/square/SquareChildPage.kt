package com.btpj.wanandroid.ui.main.square.square

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.btpj.wanandroid.App
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.ui.main.ArticleItem
import com.btpj.lib_base.ui.widgets.RefreshList

/**
 * @author LTP  2023/12/25
 */
@Composable
fun SquareChildPage(
    squareChildViewModel: SquareChildViewModel = viewModel(),
    lazyListState: LazyListState,
    onArticleClick: (Article) -> Unit
) {
    val needRefresh by App.appViewModel.shareArticleEvent.observeAsState()

    LaunchedEffect(key1 = Unit, block = {
        if (needRefresh == true) {
            squareChildViewModel.fetchSquarePageList()
        }
    })

    RefreshList(
        viewModel = squareChildViewModel,
        lazyListState = lazyListState,
        onRefresh = {
            squareChildViewModel.fetchSquarePageList()
        },
        onLoadMore = {
            squareChildViewModel.fetchSquarePageList(false)
        },
        itemContent = {
            ArticleItem(article = it, squareChildViewModel, onArticleClick = onArticleClick)
        })
}