package com.btpj.wanandroid.data.bean

import com.btpj.lib_base.ui.widgets.ProvideItemKey

/**
 * 积分
 *
 * @author LTP  2022/4/12
 */
data class CoinInfo(
    val coinCount: Int, // 当前积分
    val rank: String,
    val level: Int,
    val userId: Int,
    val nickname: String,
    val username: String
) : ProvideItemKey {
    override fun provideKey(): Int {
        return userId
    }
}