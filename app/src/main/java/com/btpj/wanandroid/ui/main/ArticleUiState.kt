package com.btpj.wanandroid.ui.main

import com.btpj.wanandroid.data.bean.Article

/**
 * @author LTP  2023/12/19
 */
data class ArticleUiState(
    val showLoading: Boolean,
    val showError: String?,
    val list: List<Article>?,
    val showLoadMoreLoading: Boolean, // 加载更多时展示加载框
    val noMoreData: Boolean // 无更多数据
)
