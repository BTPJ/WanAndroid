package com.btpj.lib_base.ext

import androidx.lifecycle.viewModelScope
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.bean.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * BaseViewModel的一些扩展方法
 *
 * @author LTP  2022/3/22
 */
fun BaseViewModel.request(
    tryBlock: suspend CoroutineScope.() -> Unit,
    catchBlock: suspend CoroutineScope.() -> Unit = {},
    finallyBlock: suspend CoroutineScope.() -> Unit = {}
) {
    // 默认是执行在主线程，相当于launch(Dispatchers.Main)
    viewModelScope.launch {
        try {
            tryBlock()
        } catch (e: Exception) {
            exception.value = e
            catchBlock()
        } finally {
            finallyBlock()
        }
    }
}

/**
 * 处理请求结果
 */
suspend fun <T> BaseViewModel.handleResponse(
    response: ApiResponse<T>,
    successBlock: suspend CoroutineScope.() -> Unit = {},
    errorBlock: suspend CoroutineScope.() -> Unit = {}
) {
    coroutineScope {
        // 统一处理服务器返回错误码的情况
        if (response.errorCode == -1) {
            errorMsg.value = response.errorMsg
            errorBlock()
        } else {
            successBlock()
        }
    }
}