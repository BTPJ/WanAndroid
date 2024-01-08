package com.btpj.wanandroid.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.btpj.lib_base.utils.LogUtil
import com.btpj.lib_base.utils.ToastUtil
import com.btpj.wanandroid.App
import com.btpj.wanandroid.R
import com.btpj.wanandroid.ui.main.MainPage
import com.btpj.wanandroid.ui.theme.WanAndroidTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 主页
 *
 * @author LTP 2022/3/9
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanAndroidTheme {
                MainPage()
            }
        }

        startObserver()

        // 返回处理
        onBackPressedDispatcher.addCallback(this, mBackPressedCallback)
    }

    /** 全局错误处理 */
    private fun startObserver() {
        App.appViewModel.apply {
            exception.observe(this@MainActivity) {
                LogUtil.e("网络请求错误：${it?.message}")
                when (it) {
                    is SocketTimeoutException -> ToastUtil.showShort(
                        this@MainActivity,
                        getString(com.btpj.lib_base.R.string.request_time_out)
                    )

                    is ConnectException, is UnknownHostException -> ToastUtil.showShort(
                        this@MainActivity,
                        getString(com.btpj.lib_base.R.string.network_error)
                    )

                    else -> ToastUtil.showShort(
                        this@MainActivity,
                        it?.message ?: getString(com.btpj.lib_base.R.string.response_error)
                    )
                }

                // 全局服务器返回的错误信息监听
                errorResponse.observe(this@MainActivity) { response ->
                    response?.let { it1 -> ToastUtil.showShort(this@MainActivity, it1.errorMsg) }
                }
            }
        }
    }

    /** 上一次点击返回键的时间 */
    private var lastBackMills: Long = 0

    /** 返回监听回调 */
    private val mBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (System.currentTimeMillis() - lastBackMills > 2000) {
                lastBackMills = System.currentTimeMillis()
                ToastUtil.showShort(
                    this@MainActivity,
                    getString(R.string.toast_double_back_exit)
                )
            } else {
                finish()
            }
        }
    }
}