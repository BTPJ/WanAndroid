package com.btpj.wanandroid.data.bean

import com.btpj.lib_base.ui.widgets.ProvideItemKey

/**
 * 导航实体
 * @author LTP  2022/4/7
 */
data class Navigation(
    var articles: List<Article>,
    var cid: Int,
    var name: String
) : ProvideItemKey {
    override fun provideKey(): Int {
        return cid
    }
}