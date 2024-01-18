package com.btpj.wanandroid.ui.collect.article

import com.btpj.wanandroid.ui.main.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectArticleViewModel @Inject constructor() : ArticleViewModel() {

    /** 请求收藏文章分页列表 */
    fun fetchCollectArticlePageList(isRefresh: Boolean = true) {
        getArticlePageList(ArticleType.Collect, isRefresh)
    }
}