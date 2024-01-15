package com.btpj.wanandroid.data.bean

/**
 * 轮播图实体
 *
 * @author LTP  2022/3/22
 */
data class Banner(
    var desc: String = "",
    var id: Int = 0,
    var imagePath: String = "",
    var isVisible: Int = 0,
    var order: Int = 0,
    var title: String = "",
    var type: Int = 0,
    var url: String = ""
)