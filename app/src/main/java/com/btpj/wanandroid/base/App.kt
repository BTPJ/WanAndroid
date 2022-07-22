package com.btpj.wanandroid.base

import com.btpj.lib_base.BaseApp
import com.btpj.wanandroid.BuildConfig
import com.tencent.bugly.Bugly

/**
 * Application基类
 *
 * @author LTP  2022/3/21
 */
class App : BaseApp() {

    companion object {
        lateinit var appViewModel: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        appViewModel = getAppViewModelProvider().get(AppViewModel::class.java)

        // bugly初始化
        Bugly.init(applicationContext, "99ff7c64d9", false)
    }
}