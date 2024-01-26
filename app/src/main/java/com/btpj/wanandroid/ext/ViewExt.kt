package com.btpj.wanandroid.ext

import android.content.Context
import android.os.Build
import android.view.View
import android.view.Window
import com.btpj.wanandroid.data.local.UserManager

/**
 * 一些扩展函数
 * @author LTP  2022/4/12
 */

/**
 * 需要验证登录状态的操作
 *
 * @param action 函数参数，已登录状态时的处理
 */
fun Context.launchCheckLogin(action: (context: Context) -> Unit) {
    if (UserManager.isLogin()) {
        action.invoke(this)
    } else {
//        LoginActivity.launch(this)
    }
}


/**
 *  设置状态栏是否透明，即是否取消沉浸式状态栏
 *  @param fits true表示取消沉浸式状态栏，即状态栏为透明色
 */
fun Window.decorFitsSystemWindows(fits: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        setDecorFitsSystemWindows(fits)
    } else {
        decorView.systemUiVisibility =
            if (fits) View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN else
                View.SYSTEM_UI_FLAG_VISIBLE
    }
}