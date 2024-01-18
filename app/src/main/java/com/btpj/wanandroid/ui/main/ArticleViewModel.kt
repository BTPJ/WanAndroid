package com.btpj.wanandroid.ui.main

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.wanandroid.App
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.CollectData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author LTP  2023/12/19
 */
open class ArticleViewModel : BaseViewModel<List<Article>>() {

    /** 我收藏的文章列表中取消收藏 */
    private val _unCollectEvent = MutableStateFlow<Int?>(null)
    val unCollectEvent: StateFlow<Int?> = _unCollectEvent

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
        authorId: Int? = null,
        searchKey: String? = null
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

                ArticleType.Search -> DataRepository.getSearchDataByKey(
                    currentPage, PAGE_SIZE, searchKey ?: ""
                )

                ArticleType.SystemDetails -> DataRepository.getArticlePageList(
                    currentPage, PAGE_SIZE, categoryId!!
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
    fun handleCollect(
        article: Article,
        isInCollectPage: Boolean = false, // 是否是我的收藏页面
        successCallBack: () -> Unit = {}
    ) {
        launch({
            val response = if (isInCollectPage) {
                DataRepository.unCollectArticleInCollectPage(article.id, article.originId)
            } else {
                if (article.collect) DataRepository.unCollectArticle(article.id) else
                    DataRepository.collectArticle(article.id)
            }
            handleRequest(response) {
                // 刷新article收藏状态
                App.appViewModel.emitCollectEvent(
                    CollectData(
                        article.id,
                        article.link,
                        if (isInCollectPage) false else !article.collect
                    )
                )
                if (isInCollectPage) {
                    _unCollectEvent.value = article.id
                }
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
        object Search : ArticleType()    // 搜索到的文章
        object SystemDetails : ArticleType()    // 搜索到的文章
    }
}