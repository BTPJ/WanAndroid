package com.btpj.wanandroid.data.bean

import com.btpj.lib_base.data.bean.PageResponse

/**
 * ζηεδΊ«
 *
 * @author LTP  2022/4/13
 */
data class Share(
    var coinInfo: CoinInfo,
    var shareArticles: PageResponse<Article>
)