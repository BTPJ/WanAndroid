package com.btpj.wanandroid.ui.main.square.ask

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.ui.main.ArticleItem
import com.btpj.wanandroid.ui.main.ArticleRefreshList
import com.btpj.wanandroid.ui.main.ArticleViewModel

/**
 * @author LTP  2023/12/25
 */
@Composable
fun AskPage(
    askViewModel: AskViewModel = viewModel(),
    lazyListState: LazyListState,
    onArticleClick: (Article) -> Unit
) {
    ArticleRefreshList(
        viewModel = askViewModel,
        lazyListState = lazyListState,
        onRefresh = {
            askViewModel.fetchAskPageList()
        },
        onLoadMore = {
            askViewModel.fetchAskPageList(false)
        }) {
        ArticleItem(article = it, onArticleClick = onArticleClick)
    }
}