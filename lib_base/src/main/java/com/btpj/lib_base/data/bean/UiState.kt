package com.btpj.lib_base.data.bean

/**
 * @author LTP  2023/12/19
 */
open class UiState<T>(
    val showLoading: Boolean = false,
    var data: T? = null,
    val error: String? = null,
    val showLoadingMore: Boolean = false,
    val noMoreData: Boolean = false
)
