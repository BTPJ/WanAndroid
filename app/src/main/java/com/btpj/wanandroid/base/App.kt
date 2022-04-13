package com.btpj.wanandroid.base

import com.btpj.lib_base.BaseApp
import com.tencent.mmkv.MMKV

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

        // MMKV初始化
        MMKV.initialize(this)
    }
}