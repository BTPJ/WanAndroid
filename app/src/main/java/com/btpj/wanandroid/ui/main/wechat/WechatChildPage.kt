package com.btpj.wanandroid.ui.main.wechat

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.btpj.lib_base.utils.LogUtil
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.ui.main.ArticleItem
import com.btpj.wanandroid.ui.main.ArticleRefreshList
import com.btpj.wanandroid.ui.main.ArticleViewModel

/**
 * @author LTP  2023/12/21
 */
@Composable
fun WechatChildPage(
    authorId: Int,
    lazyListState: LazyListState,
    wechatChildViewModel: WechatChildViewModel = viewModel(key = "$authorId"),
    onArticleClick: (Article) -> Unit
) {
    ArticleRefreshList(
        viewModel = wechatChildViewModel,
        lazyListState = lazyListState,
        onRefresh = {
            wechatChildViewModel.fetchAuthorPageList(authorId)
        },
        onLoadMore = {
            wechatChildViewModel.fetchAuthorPageList(authorId, false)
        }) {
        ArticleItem(article = it, onArticleClick = onArticleClick)
    }
}