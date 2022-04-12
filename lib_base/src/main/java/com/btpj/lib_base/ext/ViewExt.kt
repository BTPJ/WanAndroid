package com.btpj.lib_base.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.btpj.lib_base.R

/**
 * SwipeRefreshLayout设置加载主题颜色
 * @author LTP  2022/3/24
 */
fun SwipeRefreshLayout.initColors() {
    setColorSchemeResources(
        android.R.color.holo_blue_light, android.R.color.holo_red_light,
        android.R.color.holo_orange_light, android.R.color.holo_green_light
    )
}

/**
 * RecyclerView列表为空时的显示视图
 */
fun RecyclerView.getEmptyView(): View {
    return LayoutInflater.from(context)
        .inflate(R.layout.layout_empty, parent as ViewGroup, false)
}

/**
 * 初始化普通的toolbar 只设置标题
 *
 * @param titleStr 标题
 */
fun Toolbar.initTitle(titleStr: String = "") {
    title = titleStr
}

/**
 * 初始化返回键
 *
 * @param backImg 返回键资源路径
 * @param onBack 返回事件
 */
fun Toolbar.initClose(
    backImg: Int = R.drawable.ic_back,
    onBack: () -> Unit
) {
    setNavigationIcon(backImg)
    setNavigationOnClickListener { onBack() }
}
