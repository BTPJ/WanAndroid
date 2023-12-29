package com.btpj.wanandroid.ui.main.square.ask

import com.btpj.wanandroid.ui.main.ArticleViewModel

/**
 * @author LTP  2023/12/19
 */
class AskViewModel : ArticleViewModel() {

    /** 请求每日一问分页列表 */
    fun fetchAskPageList(isRefresh: Boolean = true) {
        getArticlePageList(ArticleType.Ask, isRefresh)
    }
}