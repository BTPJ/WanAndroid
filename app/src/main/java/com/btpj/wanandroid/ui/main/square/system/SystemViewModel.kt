package com.btpj.wanandroid.ui.main.square.system

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Structure

class SystemViewModel : BaseViewModel<Structure>() {

    /** 请求体系列表 */
    fun fetchSystemList() {
        val list = ArrayList<Structure>()
        emitUiState(true, list = list)
        launch({
            handleRequest(DataRepository.getTreeList()) {
                emitUiState(false, list = list.apply { addAll(it.data) })
            }
        })
    }
}