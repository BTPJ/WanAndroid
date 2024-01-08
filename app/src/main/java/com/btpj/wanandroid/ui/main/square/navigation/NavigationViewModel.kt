package com.btpj.wanandroid.ui.main.square.navigation

import com.btpj.wanandroid.base.BaseViewModel
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Navigation

class NavigationViewModel : BaseViewModel<List<Navigation>>() {

    /** 请求导航列表 */
    fun fetchNavigationList() {
        emitUiState(true)
        launch({
            handleRequest(DataRepository.getNavigationList())
            {
                emitUiState(data = it.data)
            }
        })
    }
}