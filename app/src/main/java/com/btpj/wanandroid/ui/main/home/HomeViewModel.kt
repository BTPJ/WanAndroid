package com.btpj.wanandroid.ui.main.home

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.lib_base.utils.LogUtil
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.Banner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 首页ViewModel
 *
 * @author LTP 2022/3/23
 */
class HomeViewModel : BaseViewModel() {

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    /** Banner列表 */
    private val _bannerListStateFlow = MutableStateFlow<List<Banner>>(emptyList())
    val bannerListStateFlow = _bannerListStateFlow

    /** 文章列表 */
    val articlePageListLiveData = MutableLiveData<PageResponse<Article>?>()

    override fun start() {}

    /** 请求首页轮播图 */
    fun fetchBanners() {
        launch({
            handleRequest(DataRepository.getBanner(), {
                _bannerListStateFlow.value = it.data
            })
        })
    }

    /**
     * 请求文章列表，第一页包括一个请求置顶的接口和一个文章分页列表的接口
     *
     * @param pageNo 页码（0表示请求第1页）
     */
    fun fetchArticlePageList(pageNo: Int = 0) {
        launch({
            if (pageNo == 0) {
                // 第一页会同时请求置顶文章列表接口和分页文章列表的接口，使用async进行并行请求速度更快（默认是串行的）
                val response1 =
                    async(Dispatchers.IO) { DataRepository.getArticlePageList(pageNo, PAGE_SIZE) }
                val response2 = async(Dispatchers.IO) { DataRepository.getArticleTopList() }

                handleRequest(response1.await(), {
                    val list = response1.await()
                    handleRequest(response2.await(), {
                        (list.data.datas as ArrayList<Article>).addAll(
                            0,
                            response2.await().data
                        )
                        articlePageListLiveData.value = list.data
                    })
                })
            } else {
                handleRequest(
                    DataRepository.getArticlePageList(pageNo, PAGE_SIZE),
                    {
                        articlePageListLiveData.value = it.data
                    })
            }
        })
    }

    /**
     * 收藏文章
     * @param id 文章id
     */
    fun collectArticle(id: Int, successCallBack: () -> Any? = {}) {
        launch({
            handleRequest(DataRepository.collectArticle(id), {
                successCallBack.invoke()
            })
        })
    }

    /**
     * 取消收藏文章
     * @param id 文章id
     */
    fun unCollectArticle(id: Int, successCallBack: () -> Any? = {}) {
        launch({
            handleRequest(DataRepository.unCollectArticle(id), {
                successCallBack.invoke()
            })
        })
    }
}