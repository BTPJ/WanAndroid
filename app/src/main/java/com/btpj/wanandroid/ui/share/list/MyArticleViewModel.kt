package com.btpj.wanandroid.ui.share.list

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article

class MyArticleViewModel : BaseViewModel<List<Article>>() {

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    private val articleList = ArrayList<Article>()
    private var currentPage = 0

    /**
     * 获取我分享的文章分页列表
     *
     * @param isRefresh 是否是下拉刷新
     */
    fun fetchMyShareArticlePageList(isRefresh: Boolean = true) {
        launch({
            emitUiState(isRefresh, articleList)
            if (isRefresh) {
                articleList.clear()
                currentPage = 0
            }
            handleRequest(
                DataRepository.getMyShareArticlePageList(currentPage, PAGE_SIZE)
            ) {
                articleList.addAll(it.data.shareArticles.datas)
                if (articleList.size == it.data.shareArticles.total) {
                    emitUiState(
                        data =
                        articleList, showLoadingMore = true, noMoreData = true
                    )
                    return@handleRequest
                }
                currentPage++

                emitUiState(data = articleList, showLoadingMore = true)
            }
        })

    }

    /**
     * 删除我分享的文章
     *
     * @param id 要删除的文章id
     */
    fun deleteMyShareArticle(id: Int, deleteSuccess: () -> Any = {}) {
        launch({
            val response = DataRepository.deleteShareArticle(id)
            handleRequest(response) {
                // 删除成功
                emitUiState(data = uiState.value?.data?.filter { it.id != id })
                deleteSuccess.invoke()
            }
        })
    }
}