package com.btpj.lib_base.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

/**
 * 屏幕相关工具类
 *   1.设置沉浸式任务栏
 *
 * @author LTP 16/9/21.
 */
object StatusBarUtil {

    /**
     * 设置透明状态栏
     *
     * @param activity 要设置的Activity
     */
    fun setImmersionStatus(activity: Activity) {
        // 透明状态栏
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    /**
     * 设置Android6.0上状态栏的字体颜色为黑色
     *
     * @param activity Activity
     */
    fun setStatusBarLightMode(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    /**
     * 设置Android6.0上状态栏的字体颜色为黑色
     *
     * @param activity Activity
     */
    fun setStatusBarDarkMode(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    /**
     * 获取手机状态栏的高度
     */
    fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }
}
