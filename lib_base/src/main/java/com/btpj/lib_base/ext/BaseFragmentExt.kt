package com.btpj.lib_base.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.btpj.lib_base.R

/**
 * Fragment基类的一些扩展类
 *
 * @author LTP  2022/3/24
 */
fun Fragment.getEmptyView(recyclerView: RecyclerView): View {
    return LayoutInflater.from(context)
        .inflate(R.layout.layout_empty, recyclerView.parent as ViewGroup, false)
}