package com.btpj.wanandroid.ui.wechat

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.ui.main.ListUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

/**
 * @author LTP  2023/12/19
 */
class WechatViewModel : BaseViewModel() {

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    private val _uiState = MutableLiveData<ListUiState<Article>>()
    val uiState = _uiState

    private val articleList = arrayListOf<Article>()
    private var currentPage = 0

    override fun start() {

    }

    /**
     * 请求文章列表，第一页包括一个请求置顶的接口和一个文章分页列表的接口
     *
     * @param isRefresh 是否是下拉刷新
     */
    fun fetchArticlePageList(isRefresh: Boolean = true) {
        launch({
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

                    }
                }
            } else {
                handleRequest(
                    DataRepository.getArticlePageList(currentPage++, PAGE_SIZE))
                    {

                    }
            }
        })
    }

    /** 请求首页轮播图 */
    fun fetchBanners() {
        launch({
            handleRequest(DataRepository.getBanner()) {
//                _bannerListStateFlow.value = it.data
            }
        })
    }
}