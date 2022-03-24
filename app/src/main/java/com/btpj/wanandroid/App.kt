package com.btpj.wanandroid

import com.btpj.lib_base.BaseApp
import com.tencent.mmkv.MMKV

/**
 * Application基类
 *
 * @author LTP  2022/3/21
 */
class App : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        // MMKV初始化
        MMKV.initialize(this)
    }
}