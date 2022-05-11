package com.btpj.lib_base.utils

import android.content.Context
import android.os.Build

/**
 * APP常用工具类，包括获取版本号等
 *
 * @author LTP  2022/4/12
 */
object AppUtil {

    /** 获取版本号名称 */
    fun getAppVersionName(context: Context): String {
        val packageInfo =
            context.applicationContext.packageManager.getPackageInfo(context.packageName, 0)
        return packageInfo.versionName
    }

    /** 获取版本号 */
    fun getAppVersionCode(context: Context): Long {
        val packageInfo =
            context.applicationContext.packageManager.getPackageInfo(context.packageName, 0)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) packageInfo.longVersionCode
        else packageInfo.versionCode.toLong()
    }
}