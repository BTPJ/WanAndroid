package com.btpj.wanandroid.ui.main.square.system

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleResponse
import com.btpj.lib_base.ext.request
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.System

class SystemViewModel : BaseViewModel() {

    /** 体系列表LiveData */
    val systemListLiveData = MutableLiveData<List<System>>()

    override fun start() {

    }

    /** 请求体系列表 */
    fun fetchSystemList(pageNo: Int = 0) {
        request({
            DataRepository.getTreeList().let {
                handleResponse(it, { systemListLiveData.value = it.data })
            }
        })
    }
}