package com.btpj.wanandroid.ui.main.square.square

import com.btpj.wanandroid.ui.main.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author LTP  2023/12/19
 */
@HiltViewModel
class SquareChildViewModel @Inject constructor() : ArticleViewModel() {

    /** 请求广场分页列表 */
    fun fetchSquarePageList(isRefresh: Boolean = true) {
        getArticlePageList(ArticleType.Square, isRefresh)
    }
}