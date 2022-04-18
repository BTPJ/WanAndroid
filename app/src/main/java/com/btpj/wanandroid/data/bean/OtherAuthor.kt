package com.btpj.wanandroid.data.bean

import com.btpj.lib_base.data.bean.PageResponse

/**
 * 其他文章作者信息实体
 * @author LTP  2022/4/18
 */
data class OtherAuthor(
    val coinInfo: CoinInfo,
    val shareArticles: PageResponse<Article>
)