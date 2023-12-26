package com.btpj.wanandroid.ui.main

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.base.UiStatus
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

/**
 * @author LTP  2023/12/19
 */
class ArticleViewModel : BaseViewModel<List<Article>>() {

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    private val articleList = arrayListOf<Article>()
    private var currentPage = 0

    /**
     * 请求文章列表，第一页包括一个请求置顶的接口和一个文章分页列表的接口
     *
     * @param isRefresh 是否是下拉刷新
     */
    fun fetchHomeArticlePageList(isRefresh: Boolean = true) {
        emitUiState(UiStatus.Loading)
        launch({
            if (isRefresh) {
                articleList.clear()
                currentPage = 0
            }

            if (currentPage == 0) {
                // 第一页会同时请求置顶文章列表接口和分页文章列表的接口，使用async进行并行请求速度更快（默认是串行的）
                val job1 = async(Dispatchers.IO) {
                    DataRepository.getArticlePageList(
                        currentPage++, PAGE_SIZE
                    )
                }
                val job2 = async(Dispatchers.IO) { DataRepository.getArticleTopList() }

                val response1 = job1.await()
                val response2 = job2.await()

                handleRequest(response1) {
                    handleRequest(response2) {
                        (response1.data.datas as ArrayList<Article>).addAll(
                            0, response2.data
                        )
                        emitUiState(
                            UiStatus.Success(
                                articleList.apply { addAll(response1.data.datas) },
                                showLoadingMore = true,
                            )
                        )
                    }
                }
            } else {
                handleRequest(DataRepository.getArticlePageList(currentPage++, PAGE_SIZE)) {
                    articleList.addAll(it.data.datas)
                    if (articleList.size == it.data.total) {
                        emitUiState(
                            UiStatus.Success(
                                articleList, showLoadingMore = true, noMoreData = true
                            )
                        )
                        return@handleRequest
                    }
                    currentPage++

                    emitUiState(
                        UiStatus.Success(articleList, showLoadingMore = true)
                    )
                }
            }
        })
    }

    /** 请求最新项目分页列表 */
    fun fetchNewProjectPageList(isRefresh: Boolean = true) {
        getArticlePageList(ArticleType.LatestProject, isRefresh)
    }

    /** 请求项目分页列表 */
    fun fetchProjectPageList(categoryId: Int, isRefresh: Boolean = true) {
        getArticlePageList(ArticleType.Project, isRefresh, categoryId = categoryId)
    }

    /** 请求每日一问分页列表 */
    fun fetchAskPageList(isRefresh: Boolean = true) {
        getArticlePageList(ArticleType.Ask, isRefresh)
    }

    /** 请求广场分页列表 */
    fun fetchSquarePageList(isRefresh: Boolean = true) {
        getArticlePageList(ArticleType.Square, isRefresh)
    }

    /** 请求公众号作者文章分页列表 */
    fun fetchAuthorPageList(authorId: Int, isRefresh: Boolean = true) {
        getArticlePageList(ArticleType.Wechat, isRefresh, authorId = authorId)
    }

    private fun getArticlePageList(
        articleType: ArticleType, isRefresh: Boolean = true, categoryId: Int = 0, authorId: Int = 0
    ) {
        emitUiState(UiStatus.Loading)
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
                    currentPage, PAGE_SIZE, categoryId
                )

                ArticleType.Square -> DataRepository.getSquarePageList(
                    currentPage, PAGE_SIZE
                )

                ArticleType.Ask -> DataRepository.getAskPageList(
                    currentPage, PAGE_SIZE
                )

                ArticleType.Wechat -> DataRepository.getAuthorArticlePageList(
                    authorId, currentPage, PAGE_SIZE
                )
            }

            handleRequest(response) {
                articleList.addAll(it.data.datas)
                if (articleList.size == it.data.total) {
                    emitUiState(
                        UiStatus.Success(articleList, showLoadingMore = false, noMoreData = true)
                    )
                    return@handleRequest
                }
                currentPage++
                emitUiState(UiStatus.Success(articleList, showLoadingMore = false))
            }
        })
    }

    sealed class ArticleType {
        object LatestProject : ArticleType()        // 最新项目
        object Project : ArticleType()    // 项目列表
        object Square : ArticleType()    // 广场 - 广场
        object Ask : ArticleType()    // 广场 - 每日一问
        object Wechat : ArticleType()    // 公众号
    }
}