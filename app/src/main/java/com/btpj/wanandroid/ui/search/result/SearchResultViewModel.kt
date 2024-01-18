package com.btpj.wanandroid.ui.search.result

import com.btpj.wanandroid.ui.main.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author LTP  2022/4/19
 */
@HiltViewModel
class SearchResultViewModel @Inject constructor() : ArticleViewModel() {

    /**
     * 请求搜索结果分页列表
     * @param searchKeyStr 搜索关键词
     * @param isRefresh 是否是刷新
     */
    fun fetchSearchResultPageList(searchKeyStr: String, isRefresh: Boolean = true) {
        getArticlePageList(ArticleType.Search, isRefresh, searchKey = searchKeyStr)
    }
}