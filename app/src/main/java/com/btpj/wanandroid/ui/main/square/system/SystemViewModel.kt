package com.btpj.wanandroid.ui.main.square.system

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Structure

class SystemViewModel : BaseViewModel<List<Structure>>() {

    /** 请求体系列表 */
    fun fetchSystemList() {
        emitUiState(true)
        launch({
            handleRequest(DataRepository.getTreeList()) {
                emitUiState(data = it.data)
            }
        })
    }
}