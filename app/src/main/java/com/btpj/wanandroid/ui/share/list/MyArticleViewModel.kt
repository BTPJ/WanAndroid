package com.btpj.wanandroid.ui.share.list

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article

class MyArticleViewModel : BaseViewModel() {
    val articlePageList = MutableLiveData<PageResponse<Article>>()

    override fun start() {}

    /**
     * 获取我分享的文章分页列表
     *
     * @param pageNo 页码
     */
    fun fetchMyShareArticlePageList(pageNo: Int = 1) {
        launch({
            val response = DataRepository.getMyShareArticlePageList(pageNo)
            handleRequest(response, {
                articlePageList.value = response.data.shareArticles
            })
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
            handleRequest(response, {
                deleteSuccess.invoke()
            })
        })
    }
}