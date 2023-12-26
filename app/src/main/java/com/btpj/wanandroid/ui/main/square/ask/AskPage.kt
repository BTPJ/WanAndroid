package com.btpj.wanandroid.ui.main.square.ask

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
    articleViewModel: ArticleViewModel = viewModel(),
    onArticleClick: (Article) -> Unit
) {
    ArticleRefreshList(
        articleViewModel = articleViewModel,
        onRefresh = {
            articleViewModel.fetchAskPageList()
        },
        onLoadMore = {
            articleViewModel.fetchAskPageList(false)
        }) {
        ArticleItem(article = it, onArticleClick = onArticleClick)
    }
}