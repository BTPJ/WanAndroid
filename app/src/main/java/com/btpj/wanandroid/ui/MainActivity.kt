package com.btpj.wanandroid.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import com.btpj.lib_base.utils.ToastUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.ui.main.MainPage
import com.btpj.wanandroid.ui.theme.WanAndroidTheme

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

        // 返回处理
        onBackPressedDispatcher.addCallback(this, mBackPressedCallback)
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