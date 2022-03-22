package com.btpj.wanandroid.data.bean

/**
 * 轮播图实体
 *
 * @author LTP  2022/3/22
 */
data class Banner(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)