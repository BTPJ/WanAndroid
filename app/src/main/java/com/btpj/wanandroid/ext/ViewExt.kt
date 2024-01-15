package com.btpj.wanandroid.ext

import android.content.Context
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