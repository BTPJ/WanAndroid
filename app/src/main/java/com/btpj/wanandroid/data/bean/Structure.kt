package com.btpj.wanandroid.data.bean

import com.btpj.wanandroid.ui.main.ProvideItemKey

/**
 * @author LTP  2022/4/7
 */
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
) : ProvideItemKey {
    override fun provideKey(): Int {
        return id
    }
}

