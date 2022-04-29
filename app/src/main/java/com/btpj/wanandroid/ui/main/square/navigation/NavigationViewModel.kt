package com.btpj.wanandroid.ui.main.square.navigation

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Navigation

class NavigationViewModel : BaseViewModel() {

    /** 导航列表LiveData */
    val navigationListLiveData = MutableLiveData<List<Navigation>>()

    override fun start() {}

    /** 请求导航列表 */
    fun fetchNavigationList() {
        launch({
            handleRequest(
                DataRepository.getNavigationList(),
                { navigationListLiveData.value = it.data })
        })
    }
}