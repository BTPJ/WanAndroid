package com.btpj.wanandroid.ui.main

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

/**
 * @author LTP  2023/12/19
 */
class ArticleViewModel : BaseViewModel() {

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    private val _articleUiState = MutableLiveData<ArticleUiState>()
    val articleUiState = _articleUiState

    private val articleList = arrayListOf<Article>()
    private var currentPage = 0

    override fun start() {

    }

    /**
     * 请求文章列表，第一页包括一个请求置顶的接口和一个文章分页列表的接口
     *
     * @param isRefresh 是否是下拉刷新
     */
    fun fetchHomeArticlePageList(isRefresh: Boolean = true) {
        launch({
            emitArticleUiState(isRefresh, list = articleList, showLoadMoreLoading = false)
            if (isRefresh) {
                articleList.clear()
                currentPage = 0
            }

            if (currentPage == 0) {
                // 第一页会同时请求置顶文章列表接口和分页文章列表的接口，使用async进行并行请求速度更快（默认是串行的）
                val job1 =
                    async(Dispatchers.IO) {
                        DataRepository.getArticlePageList(
                            currentPage++,
                            PAGE_SIZE
                        )
                    }
                val job2 = async(Dispatchers.IO) { DataRepository.getArticleTopList() }

                val response1 = job1.await()
                val response2 = job2.await()

                handleRequest(response1) {
                    handleRequest(response2) {
                        (response1.data.datas as ArrayList<Article>).addAll(
                            0,
                            response2.data
                        )
                        emitArticleUiState(
                            false,
                            list = articleList.apply { addAll(response1.data.datas) },
                            showLoadMoreLoading = true
                        )
                    }
                }
            } else {
                handleRequest(DataRepository.getArticlePageList(currentPage++, PAGE_SIZE))
                {
                    articleList.addAll(it.data.datas)
                    if (articleList.size == it.data.total) {
                        emitArticleUiState(
                            false,
                            list = articleList,
                            showLoadMoreLoading = false,
                            noMoreData = true
                        )
                        return@handleRequest
                    }
                    currentPage++

                    emitArticleUiState(
                        false,
                        list = articleList,
                        showLoadMoreLoading = true
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
        getArticlePageList(ArticleType.Project, isRefresh, categoryId)
    }

    private fun getArticlePageList(
        articleType: ArticleType,
        isRefresh: Boolean = true,
        categoryId: Int = 0
    ) {
        launch({
            emitArticleUiState(isRefresh, list = articleList, showLoadMoreLoading = false)
            if (isRefresh) {
                articleList.clear()
                currentPage = 0
            }

            val response = when (articleType) {
                ArticleType.LatestProject -> DataRepository.getNewProjectPageList(
                    currentPage,
                    PAGE_SIZE
                )

                ArticleType.Project -> DataRepository.getProjectPageList(
                    currentPage,
                    PAGE_SIZE,
                    categoryId
                )
            }

            handleRequest(response) {
                articleList.addAll(it.data.datas)
                if (articleList.size == it.data.total) {
                    emitArticleUiState(
                        false,
                        list = articleList,
                        showLoadMoreLoading = false,
                        noMoreData = true
                    )
                    return@handleRequest
                }
                currentPage++
                emitArticleUiState(
                    false,
                    list = articleList,
                    showLoadMoreLoading = true
                )
            }
        })
    }

    private fun emitArticleUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        list: List<Article>? = null,
        showLoadMoreLoading: Boolean = false,
        noMoreData: Boolean = false
    ) {
        val articleUiState =
            ArticleUiState(showLoading, showError, list, showLoadMoreLoading, noMoreData)
        _articleUiState.value = articleUiState
    }

    sealed class ArticleType {
        object LatestProject : ArticleType()        // 最新项目
        object Project : ArticleType()    // 项目列表
    }
}