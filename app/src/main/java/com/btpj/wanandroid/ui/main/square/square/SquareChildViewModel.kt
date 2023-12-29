package com.btpj.wanandroid.ui.main.square.square

import com.btpj.wanandroid.ui.main.ArticleViewModel

/**
 * @author LTP  2023/12/19
 */
class SquareChildViewModel : ArticleViewModel() {

    /** 请求广场分页列表 */
    fun fetchSquarePageList(isRefresh: Boolean = true) {
        getArticlePageList(ArticleType.Square, isRefresh)
    }
}