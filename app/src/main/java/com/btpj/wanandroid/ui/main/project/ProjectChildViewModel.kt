package com.btpj.wanandroid.ui.main.project

import com.btpj.wanandroid.ui.main.ArticleViewModel

/**
 * @author LTP  2023/12/19
 */
class ProjectChildViewModel : ArticleViewModel() {

    /** 请求最新项目分页列表 */
    fun fetchNewProjectPageList(isRefresh: Boolean = true) {
        getArticlePageList(ArticleType.LatestProject, isRefresh)
    }

    /** 请求项目分页列表 */
    fun fetchProjectPageList(categoryId: Int, isRefresh: Boolean = true) {
        getArticlePageList(
            ArticleType.Project,
            isRefresh,
            categoryId = categoryId
        )
    }
}