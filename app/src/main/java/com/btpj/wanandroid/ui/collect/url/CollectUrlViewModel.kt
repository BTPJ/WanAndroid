package com.btpj.wanandroid.ui.collect.url

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.bean.PageResponse
import com.btpj.lib_base.ext.handleResponse
import com.btpj.lib_base.ext.launch
import com.btpj.lib_base.utils.LogUtil
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.CollectArticle
import com.btpj.wanandroid.data.bean.CollectUrl
import com.btpj.wanandroid.data.bean.ProjectTitle

class CollectUrlViewModel : BaseViewModel() {

    /** 收藏网址列表LiveData */
    val collectUrlList = MutableLiveData<List<CollectUrl>>()

    override fun start() {}

    /** 请求收藏网址列表 */
    fun fetchCollectUrlList() {
        launch({
            DataRepository.getCollectUrlList().let {
                handleResponse(it, { collectUrlList.value = it.data })
            }
        })
    }
}