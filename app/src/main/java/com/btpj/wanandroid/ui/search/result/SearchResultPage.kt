package com.btpj.wanandroid.ui.search.result

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.ui.main.ArticleItem
import com.btpj.wanandroid.ui.main.RefreshList

/**
 * 首页Tab
 * @author LTP  2023/12/14
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SearchResultPage(
    navHostController: NavHostController,
    searchKey: String,
    searchResultViewModel: SearchResultViewModel = viewModel(),
    onArticleClick: (Article) -> Unit
) {
    Column {
        TitleBar(title = searchKey) { navHostController.popBackStack() }

        RefreshList(
            viewModel = searchResultViewModel,
            lazyListState = rememberLazyListState(),
            onRefresh = {
                searchResultViewModel.fetchSearchResultPageList(searchKey)
            },
            onLoadMore = { searchResultViewModel.fetchSearchResultPageList(searchKey, false) },
        ) {
            ArticleItem(
                article = it,
                searchResultViewModel,
                onArticleClick = onArticleClick
            )
        }
    }
}
