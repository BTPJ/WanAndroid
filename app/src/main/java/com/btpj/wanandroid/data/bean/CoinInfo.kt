package com.btpj.wanandroid.data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 积分
 *
 * @author LTP  2022/4/12
 */
@Parcelize
data class CoinInfo(
    val coinCount: Int, // 当前积分
    val rank: String,
    val level: Int,
    val userId: Int,
    val nickname: String,
    val username: String
) : Parcelable