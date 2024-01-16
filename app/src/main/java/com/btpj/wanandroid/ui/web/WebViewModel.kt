package com.btpj.wanandroid.ui.web

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.CollectUrl

/**
 * @author LTP  2022/4/2
 */
class WebViewModel : BaseViewModel<Unit>() {

    /**
     * 收藏站内文章
     * @param id 文章id
     */
    fun collectArticle(id: Int, successCallBack: () -> Unit = {}) {
        launch({
            handleRequest(DataRepository.collectArticle(id)) {
                successCallBack.invoke()
            }
        })
    }

    /**
     * 取消收藏站内文章
     * @param id 文章id
     */
    fun unCollectArticle(id: Int, successCallBack: () -> Unit = {}) {
        launch({
            handleRequest(DataRepository.unCollectArticle(id)) {
                successCallBack.invoke()
            }
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
        successCallBack: (CollectUrl: CollectUrl?) -> Unit = {}
    ) {
        launch({
            handleRequest(DataRepository.collectUrl(name, link)) {
                successCallBack.invoke(it.data)
            }
        })
    }

    /** 取消收藏网址*/
    fun unCollectUrl(id: Int, successCallBack: () -> Unit = {}) {
        launch({
            handleRequest(DataRepository.unCollectUrl(id)) {
                successCallBack.invoke()
            }
        })
    }

    /** 收藏站外文章 */
    fun collectOutSiteArticle(
        title: String,
        author: String,
        link: String,
        successCallBack: () -> Unit
    ) {
        launch({
            handleRequest(DataRepository.collectOutSiteArticle(title, author, link)) {
                successCallBack.invoke()
            }
        })
    }
}