package com.btpj.wanandroid.ui.main

import com.btpj.wanandroid.base.BaseViewModel
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article

/**
 * @author LTP  2023/12/19
 */
open class ArticleViewModel : BaseViewModel<List<Article>>() {

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    protected val articleList = ArrayList<Article>()
    protected var currentPage = 0

    protected fun getArticlePageList(
        articleType: ArticleType,
        isRefresh: Boolean = true,
        categoryId: Int? = null,
        authorId: Int? = null
    ) {
        emitUiState(isRefresh, articleList)
        launch({
            if (isRefresh) {
                articleList.clear()
                currentPage = 0
            }

            val response = when (articleType) {
                ArticleType.LatestProject -> DataRepository.getNewProjectPageList(
                    currentPage, PAGE_SIZE
                )

                ArticleType.Project -> DataRepository.getProjectPageList(
                    currentPage, PAGE_SIZE, categoryId!!
                )

                ArticleType.Square -> DataRepository.getSquarePageList(
                    currentPage, PAGE_SIZE
                )

                ArticleType.Ask -> DataRepository.getAskPageList(
                    currentPage, PAGE_SIZE
                )

                ArticleType.Wechat -> DataRepository.getAuthorArticlePageList(
                    authorId!!, currentPage, PAGE_SIZE
                )

                ArticleType.Collect -> DataRepository.getCollectArticlePageList(
                    currentPage, PAGE_SIZE
                )
            }

            handleRequest(response) {
                articleList.addAll(it.data.datas)
                if (articleList.size >= it.data.total) {
                    emitUiState(data = articleList, showLoadingMore = false, noMoreData = true)
                    return@handleRequest
                }
                currentPage++
                emitUiState(data = articleList, showLoadingMore = true)
            }
        })
    }

    /** 操作文章的收藏与取消收藏 */
    fun handleCollect(article: Article, successCallBack: () -> Unit = {}) {
        launch({
            val response =
                if (article.collect) DataRepository.unCollectArticle(article.id) else
                    DataRepository.collectArticle(article.id)
            handleRequest(response) {
                // 刷新article收藏状态
                uiState.value?.data?.forEach {
                    if (article.id == it.id) it.collect = !it.collect
                }
                emitUiState(false, uiState.value?.data)
                successCallBack.invoke()
            }
        })
    }

    sealed class ArticleType {
        object LatestProject : ArticleType()        // 最新项目
        object Project : ArticleType()    // 项目列表
        object Square : ArticleType()    // 广场 - 广场
        object Ask : ArticleType()    // 广场 - 每日一问
        object Wechat : ArticleType()    // 公众号
        object Collect : ArticleType()    // 我收藏的文章
    }
}