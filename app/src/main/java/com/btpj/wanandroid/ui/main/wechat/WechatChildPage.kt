package com.btpj.wanandroid.ui.main.wechat

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.ui.main.ArticleItem
import com.btpj.wanandroid.ui.main.RefreshList

/**
 * @author LTP  2023/12/21
 */
@Composable
fun WechatChildPage(
    authorId: Int,
    lazyListState: LazyListState,
    // authorId作为key，确保每个作者的页面使用不同的ViewModel
    wechatChildViewModel: WechatChildViewModel = viewModel(key = "$authorId"),
    onArticleClick: (Article) -> Unit
) {
    RefreshList(
        viewModel = wechatChildViewModel,
        lazyListState = lazyListState,
        onRefresh = {
            wechatChildViewModel.fetchAuthorPageList(authorId)
        },
        onLoadMore = {
            wechatChildViewModel.fetchAuthorPageList(authorId, false)
        }) {
        ArticleItem(article = it, wechatChildViewModel, onArticleClick = onArticleClick)
    }
}