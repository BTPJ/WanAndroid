package com.btpj.wanandroid.ui.collect.url

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.CollectUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectUrlViewModel @Inject constructor() : BaseViewModel<List<CollectUrl>>() {

    /** 请求收藏网址列表 */
    fun fetchCollectUrlList() {
        emitUiState(true)
        launch({
            handleRequest(DataRepository.getCollectUrlList()) {
                emitUiState(data = it.data)
            }
        })
    }

    /** 取消收藏网址*/
    fun unCollectUrl(id: Int, successCallBack: () -> Any? = {}) {
        launch({
            handleRequest(DataRepository.unCollectUrl(id)) {
                emitUiState(data = uiState.value.data?.filter { it.id != id })
                successCallBack.invoke()
            }
        })
    }
}