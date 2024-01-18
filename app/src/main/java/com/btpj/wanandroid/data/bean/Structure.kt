package com.btpj.wanandroid.data.bean

import android.os.Parcelable
import com.btpj.lib_base.ui.widgets.ProvideItemKey
import kotlinx.parcelize.Parcelize

/**
 * @author LTP  2022/4/7
 */
@Parcelize
data class Structure(
    val author: String,
    val children: List<Classify>,
    val courseId: Int,
    val cover: String,
    val desc: String,
    val id: Int,
    val lisense: String,
    val lisenseLink: String,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
) : ProvideItemKey, Parcelable {
    override fun provideKey(): Int {
        return id
    }
}

