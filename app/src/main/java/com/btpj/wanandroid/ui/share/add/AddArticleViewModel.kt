package com.btpj.wanandroid.ui.share.add

import com.btpj.lib_base.data.bean.ApiResponse
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.wanandroid.data.DataRepository

class AddArticleViewModel : BaseViewModel<Unit>() {

    /**
     * 添加分享的文章
     *
     * @param title 标题
     * @param link 文章链接
     */
    fun addArticle(
        title: String,
        link: String,
        errorCallback: () -> Unit = {},
        successCallback: (ApiResponse<Any?>) -> Unit = {}
    ) {
        launch({
            val response = DataRepository.addArticle(title, link)
            handleRequest(response, errorBlock = {
                errorCallback()
                false
            }) {
                successCallback(it)
            }
        })
    }
}