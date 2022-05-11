package com.btpj.lib_base.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * 屏幕、尺寸相关工具类
 *
 * @author LTP  2018/8/6
 */
object ScreenUtil {

    /**
     * dp转px，也可以使用resources.getDimension(R.dimen.xxx).toInt()
     *
     * @param context Context
     * @param dpVal 要转换的dp值
     *
     * @return dp转换为px后的值
     */
    fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.resources.displayMetrics).toInt()
    }

    /**
     * sp转px
     *
     * @param context Context
     * @param spVal 要转换的sp值
     *
     * @return sp转换为px后的值
     */
    fun sp2px(context: Context, spVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.resources.displayMetrics).toInt()
    }

    /**
     * px转dp
     *
     * @param context Context
     * @param pxVal 要转换的px值
     *
     * @return px转换为dp后的值
     */
    fun px2dp(context: Context, pxVal: Float): Float {
        return pxVal / context.resources.displayMetrics.density
    }

    /**
     * px转sp
     *
     * @param context Context
     * @param pxVal 要转换的px值
     *
     * @return px转换为sp后的值
     */
    fun px2sp(context: Context, pxVal: Float): Float {
        return pxVal / context.resources.displayMetrics.scaledDensity
    }

    /**
     * 获取屏幕的宽度(px)
     *
     * @param context Context
     */
    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * 获取屏幕的高度(px)
     *
     * @param context Context
     */
    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * 获取屏幕的屏幕密度
     *
     * @param context Context
     */
    fun getScreenDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }


    /**
     * 通过反射，获取包含虚拟键的整体屏幕高度
     *
     * @return 包含虚拟键的整体屏幕高度
     */
    fun getScreenRealHeight(activity: Activity): Int {
        var dpi = 0
        val display = activity.windowManager.defaultDisplay
        val dm = DisplayMetrics()
        val c: Class<*>
        try {
            c = Class.forName("android.view.Display")
            val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, dm)
            dpi = dm.heightPixels
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return dpi
    }
}