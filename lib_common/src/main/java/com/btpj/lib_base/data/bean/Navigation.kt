package com.btpj.lib_base.data.bean

import com.btpj.lib_base.data.bean.Article

/**
 * 导航实体
 * @author LTP  2022/4/7
 */
data class Navigation(
    var articles: List<Article>,
    var cid: Int,
    var name: String
)