package com.btpj.wanandroid.ui.main.square.navigation

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.base.UiState
import com.btpj.lib_base.base.UiStatus
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Navigation

class NavigationViewModel : BaseViewModel<List<Navigation>>() {

    /** 请求导航列表 */
    fun fetchNavigationList() {
        emitUiState(UiStatus.Loading)
        launch({
            handleRequest(DataRepository.getNavigationList())
            {
                emitUiState(UiStatus.Success(it.data))
            }
        })
    }
}