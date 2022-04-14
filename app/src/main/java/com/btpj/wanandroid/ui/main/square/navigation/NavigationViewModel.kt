package com.btpj.wanandroid.ui.main.square.navigation

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleResponse
import com.btpj.lib_base.ext.request
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Navigation

class NavigationViewModel : BaseViewModel() {

    /** 导航列表LiveData */
    val navigationListLiveData = MutableLiveData<List<Navigation>>()

    override fun start() {

    }

    /** 请求导航列表 */
    fun fetchNavigationList(pageNo: Int = 0) {
        request({
            DataRepository.getNavigationList().let {
                handleResponse(it, { navigationListLiveData.value = it.data })
            }
        })
    }
}