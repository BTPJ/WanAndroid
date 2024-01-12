package com.btpj.wanandroid.ui.search.result

import com.btpj.wanandroid.ui.main.ArticleViewModel

/**
 * @author LTP  2022/4/19
 */
class SearchResultViewModel : ArticleViewModel() {

    /**
     * 请求搜索结果分页列表
     * @param searchKeyStr 搜索关键词
     * @param isRefresh 是否是刷新
     */
    fun fetchSearchResultPageList(searchKeyStr: String, isRefresh: Boolean = true) {
        getArticlePageList(ArticleType.Search, isRefresh, searchKey = searchKeyStr)
    }
}