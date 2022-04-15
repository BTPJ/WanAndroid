package com.btpj.wanandroid.ui.share.add

import androidx.databinding.ObservableField
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.local.UserManager

class AddArticleViewModel : BaseViewModel() {

    val title = ObservableField<String>()
    val articleLink = ObservableField<String>()
    val shareUserName = ObservableField<String>()

    override fun start() {
        shareUserName.set(UserManager.getUser()?.nickname ?: "")
    }

    /**
     * 添加分享的文章
     *
     * @param title 标题
     * @param link 文章链接
     */
    fun addArticle(title: String, link: String, successAction: () -> Any? = {}) {
        launch({
            val response = DataRepository.addArticle(title, link)
            handleRequest(response, {
                successAction.invoke()
            })
        })
    }
}