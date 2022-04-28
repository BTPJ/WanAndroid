package com.btpj.lib_base.ext

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.btpj.lib_base.R
import com.btpj.lib_base.utils.ScreenUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * ImageView利用Glide加载图片
 * @param url 图片url（可远程可本地）
 * @param showPlaceholder 是否展示placeholder，默认为true
 */
fun ImageView.load(url: String, showPlaceholder: Boolean = true) {
    if (showPlaceholder) {
        Glide.with(context).load(url)
            .placeholder(R.drawable.ic_default_img)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(this)
    } else {
        Glide.with(context).load(url)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(this)
    }
}

/**
 * ImageView利用Glide加载图片
 * @param resourceId 本地图片资源Id
 * @param showPlaceholder 是否展示placeholder，默认为false
 */
fun ImageView.load(@DrawableRes resourceId: Int, showPlaceholder: Boolean = false) {
    // 之所以不添加withCrossFade渐变效果，是由于SplashBannerAdapter启动加载本地大图会出现滑动时图片闪烁
    if (showPlaceholder) {
        Glide.with(context).load(resourceId)
            .placeholder(R.drawable.ic_default_img)
            .into(this)
    } else {
        Glide.with(context).load(resourceId)
            .into(this)
    }
}

/**
 * ImageView利用Glide加载圆形图片
 * @param url 图片url（可远程可本地）
 */
fun ImageView.loadCircle(url: String) {
    Glide.with(context).load(url)
        .placeholder(R.drawable.ic_default_img)
        .apply(RequestOptions.bitmapTransform(CircleCrop()))
        .transition(DrawableTransitionOptions.withCrossFade(500))
        .into(this)
}

/**
 * ImageView利用Glide加载圆形图片
 * @param resourceId 本地图片资源Id
 */
fun ImageView.loadCircle(@DrawableRes resourceId: Int) {
    Glide.with(context).load(resourceId)
        .placeholder(R.drawable.ic_default_img)
        .apply(RequestOptions.bitmapTransform(CircleCrop()))
        .transition(DrawableTransitionOptions.withCrossFade(500))
        .into(this)
}

/**
 * SwipeRefreshLayout设置加载主题颜色
 * @author LTP  2022/3/24
 */
fun SwipeRefreshLayout.initColors() {
    setColorSchemeResources(
        R.color.purple_500, android.R.color.holo_red_light,
        android.R.color.holo_orange_light, android.R.color.holo_green_light
    )
}

/**
 * RecyclerView列表为空时的显示视图
 */
fun RecyclerView.getEmptyView(message: String = "列表为空"): View {
    return LayoutInflater.from(context)
        .inflate(R.layout.layout_empty, parent as ViewGroup, false).apply {
            findViewById<TextView>(R.id.tv_empty).text = message
        }
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
fun AppCompatActivity.showDialog(
    message: String,
    title: String = "温馨提示",
    positiveButtonText: String = "确定",
    positiveAction: () -> Unit = {},
    negativeButtonText: String = "取消",
    negativeAction: () -> Unit = {}
) {
    MaterialDialog(this)
        .cancelable(true)
        .lifecycleOwner(this)
        .show {
            title(text = title)
            message(text = message)
            positiveButton(text = positiveButtonText) { positiveAction.invoke() }
            negativeButton(text = negativeButtonText) { negativeAction.invoke() }
        }
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
    MaterialDialog(requireContext())
        .cancelable(true)
        .lifecycleOwner(viewLifecycleOwner)
        .show {
            title(text = title)
            message(text = message)
            positiveButton(text = positiveButtonText) { positiveAction.invoke() }
            negativeButton(text = negativeButtonText) { negativeAction.invoke() }
        }
}

/** 加载框 */
@SuppressLint("StaticFieldLeak")
private var mLoadingDialog: MaterialDialog? = null

/** 打开加载框 */
fun AppCompatActivity.showLoading(message: String = "请求网络中") {
    if (!this.isFinishing) {
        if (mLoadingDialog == null) {
            mLoadingDialog = MaterialDialog(this)
                .cancelable(true)
                .cancelOnTouchOutside(false)
                .cornerRadius(6f)
                .customView(R.layout.dialog_loading)
                .maxWidth(literal = ScreenUtil.dp2px(this, 120f))
                .lifecycleOwner(this)
            mLoadingDialog?.getCustomView()?.run {
                this.findViewById<TextView>(R.id.tv_loadingMsg).text = message
            }
        }
        mLoadingDialog?.show()
    }
}

/** 打开加载框 */
fun Fragment.showLoading(message: String = "请求网络中") {
    if (!this.isRemoving) {
        if (mLoadingDialog == null) {
            mLoadingDialog = MaterialDialog(requireContext())
                .cancelable(true)
                .cancelOnTouchOutside(false)
                .cornerRadius(6f)
                .customView(R.layout.dialog_loading)
                .maxWidth(literal = ScreenUtil.dp2px(requireContext(), 120f))
                .lifecycleOwner(this)
            mLoadingDialog?.getCustomView()?.run {
                this.findViewById<TextView>(R.id.tv_loadingMsg).text = message
            }
        }
        mLoadingDialog?.show()
    }
}

/** 隐藏Loading加载框 */
fun hideLoading() {
    mLoadingDialog?.dismiss()
    mLoadingDialog = null
}
