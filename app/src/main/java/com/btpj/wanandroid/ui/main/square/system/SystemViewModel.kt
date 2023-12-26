package com.btpj.wanandroid.ui.main.square.system

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Structure
import com.btpj.wanandroid.ui.main.ListUiState

class SystemViewModel : BaseViewModel() {

    private val _uiState = MutableLiveData<ListUiState<Structure>>()
    val uiState = _uiState

    override fun start() {}

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

    private fun emitUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        list: List<Structure>? = null
    ) {
        val uiState = ListUiState(showLoading, showError, list)
        _uiState.value = uiState
    }
}