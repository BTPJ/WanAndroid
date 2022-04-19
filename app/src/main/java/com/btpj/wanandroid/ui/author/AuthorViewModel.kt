package com.btpj.wanandroid.ui.author

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.CoinInfo

/**
 * @author LTP  2022/3/9
 */
class AuthorViewModel : BaseViewModel() {

    val coinInfo = ObservableField<CoinInfo>()
    val articlePageList = MutableLiveData<PageResponse<Article>>()

    val name = object : ObservableField<String>(coinInfo) {
        override fun get(): String {
            return coinInfo.get()?.nickname ?: "——"
        }
    }

    val info = object : ObservableField<String>(coinInfo) {
        override fun get(): String {
            return "积分：${coinInfo.get()?.coinCount ?: "——"}\t\t排名：${coinInfo.get()?.rank ?: "——"}"
        }
    }

    override fun start() {}

    /**
     * 分页获取其他作者分享的文章分页列表
     *
     * @param id 作者Id
     * @param pageNo 页码
     * @param errorCallback 请求失败的回调函数，函数返回值表示是否拦截统一的错误提示
     */
    fun fetchShareArticlePageList(
        id: Int,
        pageNo: Int = 1,
        errorCallback: () -> Boolean = { false }
    ) {
        launch({
            handleRequest(
                DataRepository.getOtherAuthorArticlePageList(id, pageNo),
                {
                    if (pageNo == 1) {
                        // 反正信息都一样，没必要每次上拉加载都更新一遍
                        coinInfo.set(it.data.coinInfo)
                    }
                    articlePageList.value = it.data.shareArticles
                }, { errorCallback.invoke() }
            )
        })
    }

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
}