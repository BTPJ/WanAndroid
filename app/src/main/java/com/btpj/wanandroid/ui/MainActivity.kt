package com.btpj.wanandroid.ui

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.btpj.lib_base.BaseApp
import com.btpj.lib_base.base.BaseActivity
import com.btpj.lib_base.utils.ToastUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.navigation.AppScreen
import com.btpj.wanandroid.navigation.Route
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 主页
 *
 * @author LTP 2022/3/9
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navHostController = rememberNavController()
            AppScreen(navHostController)
        }

        // 返回处理
        onBackPressedDispatcher.addCallback(this, mBackPressedCallback)
    }

    override fun startObserver() {
        super.startObserver()
        BaseApp.baseAppViewModel.apply {
            lifecycleScope.launch {
                errorResponse.flowWithLifecycle(lifecycle).collect {
                    if (it?.errorCode == -1001) {
                        // 需要登录，这里主要是针对收藏操作，不想每个地方都判断一下
                        // 当然你也可以封装一个收藏的组件，在里面统一判断跳转
                        navHostController.navigate(Route.LOGIN)
                    }
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