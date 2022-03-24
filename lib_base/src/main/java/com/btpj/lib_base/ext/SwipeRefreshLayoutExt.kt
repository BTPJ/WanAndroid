package com.btpj.lib_base.ext

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * SwipeRefreshLayout的一些扩展函数
 * @author LTP  2022/3/24
 */
fun SwipeRefreshLayout.initColors() {
    setColorSchemeResources(
        android.R.color.holo_blue_light, android.R.color.holo_red_light,
        android.R.color.holo_orange_light, android.R.color.holo_green_light
    )
}