package com.btpj.wanandroid.ui.main.square.ask

import com.btpj.wanandroid.ui.main.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author LTP  2023/12/19
 */
@HiltViewModel
class AskViewModel @Inject constructor() : ArticleViewModel() {

    /** 请求每日一问分页列表 */
    fun fetchAskPageList(isRefresh: Boolean = true) {
        getArticlePageList(ArticleType.Ask, isRefresh)
    }
}