package com.btpj.lib_base.data.bean

import com.btpj.lib_base.data.bean.Article
import com.btpj.lib_base.data.bean.CoinInfo
import com.btpj.lib_base.data.bean.PageResponse

/**
 * 其他文章作者信息实体
 * @author LTP  2022/4/18
 */
data class OtherAuthor(
    val coinInfo: CoinInfo,
    val shareArticles: PageResponse<Article>
)