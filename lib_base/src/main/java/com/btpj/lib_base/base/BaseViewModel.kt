package com.btpj.lib_base.base

import androidx.lifecycle.LiveData
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
    private val _uiState = MutableLiveData<UiState<T>>()
    val uiState: LiveData<UiState<T>> = _uiState

    protected fun emitUiState(uiStatus: UiStatus<T>) {
        when (uiStatus) {
            is UiStatus.Loading -> _uiState.value = UiState(true)
            is UiStatus.Success -> _uiState.value =
                UiState(
                    false,
                    uiStatus.data,
                    showLoadingMore = uiStatus.showLoadingMore,
                    noMoreData = uiStatus.noMoreData
                )

            is UiStatus.Error -> _uiState.value = UiState(false, error = uiStatus.error)
        }
    }
}