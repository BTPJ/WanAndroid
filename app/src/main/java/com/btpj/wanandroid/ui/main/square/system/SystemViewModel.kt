package com.btpj.wanandroid.ui.main.square.system

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.base.UiState
import com.btpj.lib_base.base.UiStatus
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Structure

class SystemViewModel : BaseViewModel<List<Structure>>() {

    /** 请求体系列表 */
    fun fetchSystemList() {
        emitUiState(UiStatus.Loading)
        launch({
            handleRequest(DataRepository.getTreeList()) {
                emitUiState(UiStatus.Success(it.data))
            }
        })
    }
}