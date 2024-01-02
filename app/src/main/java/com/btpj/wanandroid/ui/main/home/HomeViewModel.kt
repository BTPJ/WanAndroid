package com.btpj.wanandroid.ui.main.home

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.lib_base.utils.LogUtil
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.Banner
import com.btpj.wanandroid.ui.main.ArticleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author LTP  2023/12/19
 */
class HomeViewModel : ArticleViewModel() {

    private val _bannerList = MutableStateFlow<List<Banner>>(emptyList())
    val bannerList = _bannerList

    /** 请求首页轮播图 */
    fun fetchBanners() {
        launch({
            handleRequest(DataRepository.getBanner()) {
                _bannerList.value = it.data
            }
        })
    }

    /**
     * 请求文章列表，第一页包括一个请求置顶的接口和一个文章分页列表的接口
     *
     * @param isRefresh 是否是下拉刷新
     */
    fun fetchHomeArticlePageList(isRefresh: Boolean = true) {
        emitUiState(isRefresh, articleList)
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
                            data =
                            articleList.apply { addAll(response1.data.datas) },
                            showLoadingMore = true,
                        )
                    }
                }
            } else {
                handleRequest(DataRepository.getArticlePageList(currentPage++, PAGE_SIZE)) {
                    articleList.addAll(it.data.datas)
                    if (articleList.size == it.data.total) {
                        emitUiState(
                            data =
                            articleList, showLoadingMore = true, noMoreData = true
                        )
                        return@handleRequest
                    }
                    currentPage++

                    emitUiState(data = articleList, showLoadingMore = true)
                }
            }
        })
    }
}