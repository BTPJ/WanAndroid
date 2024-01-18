package com.btpj.wanandroid.ui.main.square.system.details

import com.btpj.wanandroid.ui.main.ArticleViewModel

/**
 * @author LTP  2023/12/19
 */
class SystemDetailsChildViewModel : ArticleViewModel() {

    /** 请求体系项目分页列表 */
    fun fetchSystemDetailsPageList(categoryId: Int, isRefresh: Boolean = true) {
        getArticlePageList(
            ArticleType.SystemDetails,
            isRefresh,
            categoryId = categoryId
        )
    }
}