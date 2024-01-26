package com.btpj.wanandroid.ext

import android.os.Bundle
import androidx.navigation.NavController
import com.btpj.wanandroid.data.local.UserManager
import com.btpj.wanandroid.navigation.Route

/**
 * Navigation的扩展函数(添加登录判断)
 * @author LTP  2023/6/20
 */
fun NavController.navigateNeedLogin(route: String, args: Bundle? = null) {
    if (!UserManager.isLogin()) {
        navigate(Route.LOGIN)
        return
    }

    graph.findNode(route)?.let {
        navigate(it.id, args)
    }
}