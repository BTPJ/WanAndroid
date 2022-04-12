package com.btpj.wanandroid.data.bean

/**
 * 积分
 *
 * @author LTP  2022/4/12
 */
data class Points(
    val coinCount: Int, // 当前积分
    val rank: Int,
    val userId: Int,
    val username: String
)