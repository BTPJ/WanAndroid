package com.btpj.wanandroid.ui.main.square.navigation

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Navigation
import com.btpj.wanandroid.ui.main.ListUiState

class NavigationViewModel : BaseViewModel() {

    private val _uiState = MutableLiveData<ListUiState<Navigation>>()
    val uiState = _uiState

    override fun start() {}

    /** 请求导航列表 */
    fun fetchNavigationList() {
        val list = ArrayList<Navigation>()
        emitUiState(true, list = list)
        launch({
            handleRequest(DataRepository.getNavigationList())
            {
                emitUiState(false, list = list.apply { addAll(it.data) })
            }
        })
    }

    private fun emitUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        list: List<Navigation>? = null
    ) {
        val uiState =
            ListUiState(showLoading, showError, list)
        _uiState.value = uiState
    }
}