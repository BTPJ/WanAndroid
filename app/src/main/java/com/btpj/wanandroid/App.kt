package com.btpj.wanandroid

import android.app.Application
import com.btpj.lib_base.BaseApp
import com.btpj.wanandroid.base.AppViewModel
import com.tencent.bugly.Bugly
import dagger.hilt.android.HiltAndroidApp

/**
 * Application基类
 *
 * @author LTP  2022/3/21
 */
@HiltAndroidApp
class App : BaseApp() {

    companion object {
        lateinit var appViewModel: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        appViewModel = getAppViewModelProvider()[AppViewModel::class.java]

        // bugly初始化
        Bugly.init(applicationContext, "99ff7c64d9", false)
    }
}