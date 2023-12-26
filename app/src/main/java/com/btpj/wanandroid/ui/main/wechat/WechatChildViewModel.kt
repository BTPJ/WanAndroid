package com.btpj.wanandroid.ui.main.wechat

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article

class WechatChildViewModel : BaseViewModel<Article>() {

    /** 项目分页列表LiveData */
    val articlePageListLiveData = MutableLiveData<PageResponse<Article>>()

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    /** 请求公众号作者文章分页列表 */
    fun fetchAuthorArticlePageList(authorId: Int, pageNo: Int = 1) {
        launch({
            handleRequest(
                DataRepository.getAuthorArticlePageList(authorId, pageNo, PAGE_SIZE)
            )
            { articlePageListLiveData.value = it.data!! }
        })
    }

    /**
     * 收藏文章
     * @param id 文章id
     */
    fun collectArticle(id: Int, successCallBack: () -> Any? = {}) {
        launch({
            handleRequest(DataRepository.collectArticle(id)) {
                successCallBack.invoke()
            }
        })
    }

    /**
     * 取消收藏文章
     * @param id 文章id
     */
    fun unCollectArticle(id: Int, successCallBack: () -> Any? = {}) {
        launch({
            handleRequest(DataRepository.unCollectArticle(id)) {
                successCallBack.invoke()
            }
        })
    }
}