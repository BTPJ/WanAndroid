package com.btpj.wanandroid.data.bean

import com.btpj.lib_base.ui.widgets.ProvideItemKey

/**
 * 收藏网址实体
 *
 * @author LTP  2022/4/13
 */
data class CollectUrl(
    var icon: String,
    var id: Int,
    var link: String,
    var name: String,
    var order: Int,
    var userId: Int,
    var visible: Int
) : ProvideItemKey {
    override fun provideKey(): Int {
        return id
    }
}