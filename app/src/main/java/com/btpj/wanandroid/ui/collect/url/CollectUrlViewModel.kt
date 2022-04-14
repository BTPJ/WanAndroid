package com.btpj.wanandroid.ui.collect.url

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleResponse
import com.btpj.lib_base.ext.request
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.CollectUrl

class CollectUrlViewModel : BaseViewModel() {

    /** 收藏网址列表LiveData */
    val collectUrlList = MutableLiveData<List<CollectUrl>>()

    override fun start() {}

    /** 请求收藏网址列表 */
    fun fetchCollectUrlList() {
        request({
            handleResponse(DataRepository.getCollectUrlList(), { collectUrlList.value = it.data })
        })
    }
}