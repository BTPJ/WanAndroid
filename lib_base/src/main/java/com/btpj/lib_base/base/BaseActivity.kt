package com.btpj.lib_base.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.btpj.lib_base.BaseApp
import com.btpj.lib_base.utils.LogUtil
import com.btpj.lib_base.utils.ToastUtil
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
    private fun startObserver() {
        BaseApp.baseAppViewModel.apply {
            exception.observe(this@BaseActivity) {
                LogUtil.e("网络请求错误：${it?.message}")
                when (it) {
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
                        it?.message ?: getString(com.btpj.lib_base.R.string.response_error)
                    )
                }
            }

            // 全局服务器返回的错误信息监听
            errorResponse.observe(this@BaseActivity) { response ->
                response?.let { it1 -> ToastUtil.showShort(this@BaseActivity, it1.errorMsg) }
            }
        }
    }
}