package com.btpj.wanandroid.ui.main.wechat

import com.btpj.wanandroid.ui.main.ArticleViewModel

/**
 * @author LTP  2023/12/19
 */
class WechatChildViewModel : ArticleViewModel() {

    /** 请求公众号作者文章分页列表 */
    fun fetchAuthorPageList(authorId: Int, isRefresh: Boolean = true) {
        getArticlePageList(ArticleType.Wechat, isRefresh, authorId = authorId)
    }
}