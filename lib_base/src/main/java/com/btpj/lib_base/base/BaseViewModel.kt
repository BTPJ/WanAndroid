package com.btpj.lib_base.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.btpj.lib_base.data.bean.ApiResponse

/**
 * ViewModel基类
 * @author LTP  2021/11/23
 */
abstract class BaseViewModel<T> : ViewModel() {

    /** 请求异常（服务器请求失败，譬如：服务器连接超时等） */
    val exception = MutableLiveData<Exception>()

    /** 请求服务器返回错误（服务器请求成功但status错误，譬如：登录过期等） */
    val errorResponse = MutableLiveData<ApiResponse<*>?>()

    /** ui状态 */
    private val _uiState = MutableLiveData<CommonUiState<T>>()
    val uiState = _uiState

    protected fun emitUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        data: T? = null,
        showLoadMoreLoading: Boolean = false,
        noMoreData: Boolean = false
    ) {
        val uiState =
            CommonUiState(showLoading, showError, data, showLoadMoreLoading, noMoreData)
        _uiState.value = uiState
    }
}