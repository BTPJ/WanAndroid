package com.btpj.wanandroid.ui.main.square.ask

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article

class AskViewModel : BaseViewModel() {

    /** 每日一问分页列表LiveData */
    val articlePageListLiveData = MutableLiveData<PageResponse<Article>>()

    override fun start() {}

    /** 请求每日一问分页列表 */
    fun fetchAskPageList(pageNo: Int = 1) {
        launch({
            DataRepository.getAskPageList(pageNo).let {
                handleRequest(it, { articlePageListLiveData.value = it.data })
            }
        })
    }
}