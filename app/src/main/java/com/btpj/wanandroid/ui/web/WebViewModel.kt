package com.btpj.wanandroid.ui.web

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.CollectUrl

/**
 * @author LTP  2022/4/2
 */
class WebViewModel : BaseViewModel() {
    override fun start() {}

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

    /**
     * 收藏网址
     * @param name 网址名
     * @param link 网址链接
     */
    fun collectUrl(
        name: String,
        link: String,
        successCallBack: (CollectUrl: CollectUrl?) -> Any? = {}
    ) {
        launch({
            handleRequest(DataRepository.collectUrl(name, link), {
                successCallBack.invoke(it.data)
            })
        })
    }

    /** 取消收藏网址*/
    fun unCollectUrl(id: Int, successCallBack: () -> Any? = {}) {
        launch({
            handleRequest(DataRepository.unCollectUrl(id), {
                successCallBack.invoke()
            })
        })
    }
}