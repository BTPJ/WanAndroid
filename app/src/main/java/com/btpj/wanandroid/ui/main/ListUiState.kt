package com.btpj.wanandroid.ui.main

/**
 * @author LTP  2023/12/19
 */
data class ListUiState<T>(
    val showLoading: Boolean,
    val showError: String?,
    val list: List<T>?,
    val showLoadMoreLoading: Boolean = false, // 加载更多时展示加载框
    val noMoreData: Boolean = false // 无更多数据
)
