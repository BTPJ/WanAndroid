package com.btpj.module_square.ui.square.system

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.lib_base.data.bean.MySystem
import com.btpj.module_square.data.DataRepository

class SystemViewModel : BaseViewModel() {

    /** 体系列表LiveData */
    val systemListLiveData = MutableLiveData<List<MySystem>>()

    override fun start() {

    }

    /** 请求体系列表 */
    fun fetchSystemList() {
        launch({
            handleRequest(DataRepository.getTreeList(), { systemListLiveData.value = it.data!! })
        })
    }
}