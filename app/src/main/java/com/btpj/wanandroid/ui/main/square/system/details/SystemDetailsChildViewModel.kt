package com.btpj.wanandroid.ui.main.square.system.details

import com.btpj.wanandroid.ui.main.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author LTP  2023/12/19
 */
@HiltViewModel
class SystemDetailsChildViewModel @Inject constructor() : ArticleViewModel() {

    /** 请求体系项目分页列表 */
    fun fetchSystemDetailsPageList(categoryId: Int, isRefresh: Boolean = true) {
        getArticlePageList(
            ArticleType.SystemDetails,
            isRefresh,
            categoryId = categoryId
        )
    }
}