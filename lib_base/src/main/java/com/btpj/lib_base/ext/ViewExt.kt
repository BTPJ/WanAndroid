package com.btpj.lib_base.ext

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
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

/**
 * Activity上显示AlertDialog
 *
 * @param message AlertDialog内容信息
 * @param title AlertDialog标题，默认为 "温馨提示"
 * @param positiveButtonText AlertDialog右侧按键内容 默认为 "确定"
 * @param positiveAction AlertDialog点击右侧按键的行为 默认是空方法
 * @param negativeButtonText AlertDialog左侧按键内容 默认为 "取消"
 * @param negativeAction AlertDialog点击左侧按键的行为 默认是空方法
 */
fun Activity.showDialog(
    message: String,
    title: String = "温馨提示",
    positiveButtonText: String = "确定",
    positiveAction: () -> Unit = {},
    negativeButtonText: String = "取消",
    negativeAction: () -> Unit = {}
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonText) { _, _ -> positiveAction.invoke() }
        .setNegativeButton(negativeButtonText) { _, _ -> negativeAction.invoke() }
        .create()
        .show()
}

/**
 * Fragment上显示AlertDialog
 *
 * @param message AlertDialog内容信息
 * @param title AlertDialog标题，默认为 "温馨提示"
 * @param positiveButtonText AlertDialog右侧按键内容 默认为 "确定"
 * @param positiveAction AlertDialog点击右侧按键的行为 默认是空方法
 * @param negativeButtonText AlertDialog左侧按键内容 默认为 "取消"
 * @param negativeAction AlertDialog点击左侧按键的行为 默认是空方法
 */
fun Fragment.showDialog(
    message: String,
    title: String = "温馨提示",
    positiveButtonText: String = "确定",
    positiveAction: () -> Unit = {},
    negativeButtonText: String = "取消",
    negativeAction: () -> Unit = {}
) {
    AlertDialog.Builder(requireContext())
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonText) { _, _ -> positiveAction.invoke() }
        .setNegativeButton(negativeButtonText) { _, _ -> negativeAction.invoke() }
        .create()
        .show()
}
