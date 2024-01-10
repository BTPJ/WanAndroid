package com.btpj.wanandroid.ui.main.square.square

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.ui.main.ArticleItem
import com.btpj.wanandroid.ui.main.ArticleRefreshList

/**
 * @author LTP  2023/12/25
 */
@Composable
fun SquareChildPage(
    squareChildViewModel: SquareChildViewModel = viewModel(),
    lazyListState: LazyListState,
    onArticleClick: (Article) -> Unit
) {
    ArticleRefreshList(
        viewModel = squareChildViewModel,
        lazyListState = lazyListState,
        onRefresh = {
            squareChildViewModel.fetchSquarePageList()
        },
        onLoadMore = {
            squareChildViewModel.fetchSquarePageList(false)
        }) {
        ArticleItem(article = it, squareChildViewModel, onArticleClick = onArticleClick)
    }
}