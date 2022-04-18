package com.btpj.wanandroid.data.bean

import com.btpj.lib_base.data.bean.PageResponse

/**
 * 我的分享
 *
 * @author LTP  2022/4/13
 */
data class Share(
    var coinInfo: CoinInfo,
    var shareArticles: PageResponse<Article>
)