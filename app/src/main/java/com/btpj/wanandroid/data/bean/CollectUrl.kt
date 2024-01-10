package com.btpj.wanandroid.data.bean

import android.os.Parcelable
import com.btpj.wanandroid.ui.main.ProvideItemKey
import kotlinx.parcelize.Parcelize

/**
 * 收藏网址实体
 *
 * @author LTP  2022/4/13
 */
@Parcelize
data class CollectUrl(
    var icon: String,
    var id: Int,
    var link: String,
    var name: String,
    var order: Int,
    var userId: Int,
    var visible: Int
) : Parcelable, ProvideItemKey {
    override fun provideKey(): Int {
        return id
    }
}