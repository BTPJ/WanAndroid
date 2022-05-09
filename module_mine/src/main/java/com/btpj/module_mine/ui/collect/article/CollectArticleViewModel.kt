package com.btpj.module_mine.ui.collect.article

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.lib_base.data.bean.CollectArticle
import com.btpj.module_mine.data.DataRepository

class CollectArticleViewModel : BaseViewModel() {

    /** 收藏文章分页列表LiveData */
    val collectArticlePageList = MutableLiveData<PageResponse<CollectArticle>>()

    override fun start() {}

    /** 请求收藏文章分页列表 */
    fun fetchCollectArticlePageList(pageNo: Int = 0) {
        launch({
            handleRequest(
                DataRepository.getCollectArticlePageList(pageNo),
                { collectArticlePageList.value = it.data })
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