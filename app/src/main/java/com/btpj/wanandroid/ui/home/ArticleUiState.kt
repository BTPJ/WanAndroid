package com.btpj.wanandroid.ui.home

import com.btpj.wanandroid.data.bean.Article

/**
 * @author LTP  2023/12/19
 */
data class ArticleUiState(
    val showLoading: Boolean,
    val showError: String?,
    val list: List<Article>?,
    val isLoadMore: Boolean, // 加载更多
)
