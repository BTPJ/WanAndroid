package com.btpj.lib_base.ext

import android.os.Bundle
import androidx.navigation.NavController

/**
 * Navigation的一些扩展函数
 * @author LTP  2023/6/20
 */
fun NavController.navigate(route: String, args: Bundle?) {
    graph.findNode(route)?.let {
        navigate(it.id, args)
    }
}