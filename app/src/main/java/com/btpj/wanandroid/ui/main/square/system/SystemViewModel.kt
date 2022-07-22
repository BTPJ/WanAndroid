package com.btpj.wanandroid.ui.main.square.system

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Structure

class SystemViewModel : BaseViewModel() {

    /** 体系列表LiveData */
    val structureListLiveData = MutableLiveData<List<Structure>>()

    override fun start() {

    }

    /** 请求体系列表 */
    fun fetchSystemList(pageNo: Int = 0) {
        launch({
            handleRequest(DataRepository.getTreeList(), { structureListLiveData.value = it.data })
        })
    }
}