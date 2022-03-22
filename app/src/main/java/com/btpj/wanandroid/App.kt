package com.btpj.wanandroid

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV
import kotlin.properties.Delegates

/**
 * Application基类
 *
 * @author LTP  2022/3/21
 */
class App : Application() {

    companion object {
        var appContext: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        // MMKV初始化
        MMKV.initialize(this)
    }
}