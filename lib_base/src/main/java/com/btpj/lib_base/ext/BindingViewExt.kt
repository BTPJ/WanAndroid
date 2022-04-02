package com.btpj.lib_base.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

/**
 * DataBinding的自定义属性
 * @author LTP  2022/4/2
 */
@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String) {
    // 利用coil装载图片
    load(url)
}