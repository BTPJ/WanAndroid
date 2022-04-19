package com.btpj.wanandroid.ui.search.result

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article

/**
 * @author LTP  2022/4/19
 */
class SearchResultViewModel : BaseViewModel() {
    val articlePageList = MutableLiveData<PageResponse<Article>?>()

    override fun start() {}

    /**
     * 请求搜索结果分页列表
     * @param searchKeyStr 搜索关键词
     * @param pageNo 页码
     */
    fun fetchSearchResultPageList(searchKeyStr: String, pageNo: Int = 0) {
        launch({
            handleRequest(DataRepository.getSearchDataByKey(pageNo, searchKeyStr), {
                articlePageList.value = it.data
            })
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