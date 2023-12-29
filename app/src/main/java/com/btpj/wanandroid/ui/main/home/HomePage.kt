package com.btpj.wanandroid.ui.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.ui.main.ArticleItem
import com.btpj.wanandroid.ui.main.ArticleRefreshList

/**
 * 首页Tab
 * @author LTP  2023/12/14
 */
@Composable
fun HomePage(
    homeViewModel: HomeViewModel = viewModel(),
    onArticleClick: (Article) -> Unit
) {
    Column {
        TitleBar(title = stringResource(id = R.string.tab_home), showBackBtn = false)
        ArticleRefreshList(
            viewModel = homeViewModel,
            lazyListState = rememberLazyListState(),
            onRefresh = { homeViewModel.fetchHomeArticlePageList() },
            onLoadMore = { homeViewModel.fetchHomeArticlePageList(false) }) {
            ArticleItem(article = it, onArticleClick = onArticleClick)
        }
    }
}