package com.btpj.wanandroid.ui.main.square.square

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.btpj.wanandroid.App
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.ui.main.ArticleItem
import com.btpj.wanandroid.ui.main.ArticleRefreshList

/**
 * @author LTP  2023/12/25
 */
@Composable
fun SquareChildPage(
    squareChildViewModel: SquareChildViewModel = hiltViewModel(),
    lazyListState: LazyListState,
    onArticleClick: (Article) -> Unit
) {
    val needRefresh by App.appViewModel.shareArticleEvent.observeAsState()

    LaunchedEffect(key1 = Unit, block = {
        if (needRefresh == true) {
            squareChildViewModel.fetchSquarePageList()
        }
    })

    ArticleRefreshList(
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