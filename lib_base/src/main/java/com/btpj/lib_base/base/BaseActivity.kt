package com.btpj.lib_base.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.btpj.lib_base.BaseApp
import com.btpj.lib_base.utils.LogUtil
import com.btpj.lib_base.utils.ToastUtil
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 主页
 *
 * @author LTP 2022/3/9
 */
open class BaseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObserver()
    }

    /** 全局错误处理 */
    protected open fun startObserver() {
        BaseApp.baseAppViewModel.apply {
            viewModelScope.launch {
                exception.flowWithLifecycle(lifecycle).collect { e ->
                    LogUtil.e("网络请求错误：${e.message}")
                    when (e) {
                        is SocketTimeoutException -> ToastUtil.showShort(
                            this@BaseActivity,
                            getString(com.btpj.lib_base.R.string.request_time_out)
                        )

                        is ConnectException, is UnknownHostException -> ToastUtil.showShort(
                            this@BaseActivity,
                            getString(com.btpj.lib_base.R.string.network_error)
                        )

                        else -> ToastUtil.showShort(
                            this@BaseActivity,
                            e.message ?: getString(com.btpj.lib_base.R.string.response_error)
                        )
                    }
                }
            }

            viewModelScope.launch {
                errorResponse.flowWithLifecycle(lifecycle).collect { response ->
                    response?.let { it1 -> ToastUtil.showShort(this@BaseActivity, it1.errorMsg) }
                }
            }
        }
    }
}