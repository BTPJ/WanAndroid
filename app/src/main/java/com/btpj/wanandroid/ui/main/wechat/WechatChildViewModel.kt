package com.btpj.wanandroid.ui.main.wechat

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article

class WechatChildViewModel : BaseViewModel() {

    /** 项目分页列表LiveData */
    val articlePageListLiveData = MutableLiveData<PageResponse<Article>>()

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    override fun start() {}

    /** 请求公众号作者文章分页列表 */
    fun fetchAuthorArticlePageList(authorId: Int, pageNo: Int = 1) {
        launch({
            DataRepository.getAuthorArticlePageList(authorId, pageNo, PAGE_SIZE).let {
                handleRequest(it, { articlePageListLiveData.value = it.data })
            }
        })
    }
}