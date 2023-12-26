package com.btpj.lib_base.base

/**
 * @author LTP  2023/12/19
 */
data class CommonUiState<T>(
    val showLoading: Boolean,
    val showError: String?,
    val data: T?,
    val showLoadMoreLoading: Boolean = false, // 加载更多时展示加载框
    val noMoreData: Boolean = false // 无更多数据
)
