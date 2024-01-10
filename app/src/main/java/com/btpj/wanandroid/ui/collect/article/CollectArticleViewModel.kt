package com.btpj.wanandroid.ui.collect.article

import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.ui.main.ArticleViewModel

class CollectArticleViewModel : ArticleViewModel() {

    /** 请求收藏文章分页列表 */
    fun fetchCollectArticlePageList(isRefresh: Boolean = true) {
        getArticlePageList(ArticleType.Collect, isRefresh)
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