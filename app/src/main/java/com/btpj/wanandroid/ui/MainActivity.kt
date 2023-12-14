package com.btpj.wanandroid.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import com.btpj.lib_base.utils.ToastUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.ext.clearLongClickToast
import com.btpj.wanandroid.ui.main.MainPage
import com.btpj.wanandroid.ui.setting.SettingPage
import com.btpj.wanandroid.ui.theme.WanAndroidTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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