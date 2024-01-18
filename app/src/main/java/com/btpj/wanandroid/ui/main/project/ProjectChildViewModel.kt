package com.btpj.wanandroid.ui.main.project

import com.btpj.wanandroid.ui.main.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author LTP  2023/12/19
 */
@HiltViewModel
class ProjectChildViewModel @Inject constructor() : ArticleViewModel() {

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