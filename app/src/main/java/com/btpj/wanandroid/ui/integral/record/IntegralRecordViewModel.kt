package com.btpj.wanandroid.ui.integral.record

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.IntegralRecord

class IntegralRecordViewModel : BaseViewModel() {
    val integralRecordPageList = MutableLiveData<PageResponse<IntegralRecord>>()

    override fun start() {}

    /**
     * 获取积分记录分页列表
     *
     * @param pageNo 页码
     */
    fun fetchIntegralRecordPageList(pageNo: Int = 1) {
        launch({
            handleRequest(DataRepository.getIntegralRecordPageList(pageNo), {
                integralRecordPageList.value = it.data!!
            })
        })
    }
}