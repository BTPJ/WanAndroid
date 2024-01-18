package com.btpj.wanandroid.ui.main.square.system.details

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.ui.main.ArticleItem
import com.btpj.wanandroid.ui.main.ArticleRefreshList

/**
 * @author LTP  2023/12/21
 */
@Composable
fun SystemDetailsChildPage(
    categoryId: Int,
    lazyListState: LazyListState,
    // 因为categoryId是动态的，所以需要添加key来确保每个categoryId对应一个ViewModel
    systemDetailsChildViewModel: SystemDetailsChildViewModel = hiltViewModel(key = "$categoryId"),
    onArticleClick: (Article) -> Unit
) {
    ArticleRefreshList(
        viewModel = systemDetailsChildViewModel,
        lazyListState = lazyListState,
        onRefresh = {
            systemDetailsChildViewModel.fetchSystemDetailsPageList(categoryId)
        },
        onLoadMore = {
            systemDetailsChildViewModel.fetchSystemDetailsPageList(categoryId, false)
        }) {
        ArticleItem(
            article = it,
            systemDetailsChildViewModel,
            onArticleClick = onArticleClick
        )
    }
}