package com.btpj.wanandroid.ui.main.square.system

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Structure
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SystemViewModel @Inject constructor() : BaseViewModel<List<Structure>>() {

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