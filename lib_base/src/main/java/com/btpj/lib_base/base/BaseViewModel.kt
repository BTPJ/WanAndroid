package com.btpj.lib_base.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.btpj.lib_base.BaseApp
import com.btpj.lib_base.data.bean.ApiResponse
import com.btpj.lib_base.data.bean.UiState
import com.btpj.lib_base.utils.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel基类
 * @author LTP  2021/11/23
 */
abstract class BaseViewModel<T> : ViewModel() {

    /** ui状态 */
    private val _uiState = MutableStateFlow<UiState<T>>(UiState(true))
    val uiState: StateFlow<UiState<T>> = _uiState

    protected fun emitUiState(
        showLoading: Boolean = false,
        data: T? = null,
        error: String? = null,
        showLoadingMore: Boolean = false,
        noMoreData: Boolean = false
    ) {
        _uiState.value = UiState(showLoading, data, error, showLoadingMore, noMoreData)
    }

    /**
     * BaseViewModel的一些扩展方法
     *
     * @author LTP  2022/3/22
     */

    /**
     * 启动协程，封装了viewModelScope.launch
     *
     * @param tryBlock try语句运行的函数
     * @param catchBlock catch语句运行的函数，可以用来做一些网络异常等的处理，默认空实现
     * @param finallyBlock finally语句运行的函数，可以用来做一些资源回收等，默认空实现
     */
    fun launch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.() -> Unit = {},
        finallyBlock: suspend CoroutineScope.() -> Unit = {}
    ) {
        // 默认是执行在主线程，相当于launch(Dispatchers.Main)
        viewModelScope.launch {
            try {
                tryBlock()
            } catch (e: Exception) {
                BaseApp.baseAppViewModel.emitException(e)
                catchBlock()
            } finally {
                finallyBlock()
            }
        }
    }

    /**
     * 请求结果处理
     *
     * @param response ApiResponse
     * @param successBlock 服务器请求成功返回成功码的执行回调，默认空实现
     * @param errorBlock 服务器请求成功返回错误码的执行回调，默认返回false的空实现，函数返回值true:拦截统一错误处理，false:不拦截
     */
    suspend fun <T> handleRequest(
        response: ApiResponse<T>,
        errorBlock: suspend CoroutineScope.(response: ApiResponse<T>) -> Boolean = { false },
        successBlock: suspend CoroutineScope.(response: ApiResponse<T>) -> Unit = {}
    ) {
        coroutineScope {
            when (response.errorCode) {
                0 -> successBlock(response) // 服务器返回请求成功码
                else -> { // 服务器返回的其他错误码
                    if (!errorBlock(response)) {
                        // 只有errorBlock返回false不拦截处理时，才去统一提醒错误提示
                        BaseApp.baseAppViewModel.emitErrorResponse(response)
                    }
                }
            }
        }
    }
}