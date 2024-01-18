package com.btpj.wanandroid.ui.main.square.system.details

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.ui.main.ArticleItem
import com.btpj.wanandroid.ui.main.ArticleRefreshList
import com.btpj.wanandroid.ui.main.project.ProjectArticleItem

/**
 * @author LTP  2023/12/21
 */
@Composable
fun SystemDetailsChildPage(
    categoryId: Int,
    lazyListState: LazyListState,
    // 因为categoryId是动态的，所以需要添加key来确保每个categoryId对应一个ViewModel
    systemDetailsChildViewModel: SystemDetailsChildViewModel = viewModel(key = "$categoryId"),
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