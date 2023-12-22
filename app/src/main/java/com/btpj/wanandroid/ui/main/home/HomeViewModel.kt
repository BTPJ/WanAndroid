package com.btpj.wanandroid.ui.main.home

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository

/**
 * @author LTP  2023/12/19
 */
class HomeViewModel : BaseViewModel() {

    override fun start() {

    }

    /** 请求首页轮播图 */
    fun fetchBanners() {
        launch({
            handleRequest(DataRepository.getBanner()) {
//                _bannerListStateFlow.value = it.data
            }
        })
    }
}