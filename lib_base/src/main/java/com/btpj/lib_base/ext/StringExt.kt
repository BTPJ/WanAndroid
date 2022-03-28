package com.btpj.lib_base.ext

import android.annotation.SuppressLint
import android.text.Html
import android.text.Spanned

/**
 * String扩展类
 * @author LTP  2022/3/25
 */
fun String.toHtml(@SuppressLint("InlinedApi") flag: Int = Html.FROM_HTML_MODE_LEGACY): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, flag)
    } else {
        Html.fromHtml(this)
    }
}