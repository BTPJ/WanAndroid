package com.btpj.lib_base

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV
import kotlin.properties.Delegates

/**
 * Application基类
 *
 * @author LTP  2022/3/21
 */
open class BaseApp : Application() {

    companion object {
        var appContext: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}