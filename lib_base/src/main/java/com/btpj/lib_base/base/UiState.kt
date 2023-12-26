package com.btpj.lib_base.base

/**
 * @author LTP  2023/12/19
 */
data class UiState<T>(
    val showLoading: Boolean = false,
    val data: T? = null,
    val error: String? = null,
    val showLoadingMore: Boolean = false,
    val noMoreData: Boolean = false
)

sealed class UiStatus<out T> {
    object Loading : UiStatus<Nothing>()

    data class Success<T>(
        val data: T,
        val showLoadingMore: Boolean = false,
        val noMoreData: Boolean = false
    ) : UiStatus<T>()

    data class Error(val error: String) : UiStatus<Nothing>()
}
