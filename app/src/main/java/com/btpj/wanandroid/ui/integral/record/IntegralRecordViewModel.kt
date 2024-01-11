package com.btpj.wanandroid.ui.integral.record

import com.btpj.wanandroid.base.BaseViewModel
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.IntegralRecord

class IntegralRecordViewModel : BaseViewModel<List<IntegralRecord>>() {

    companion object {
        /** 每页显示的条目大小 */
        private const val PAGE_SIZE = 10
    }

    private val integralRecordList = ArrayList<IntegralRecord>()
    private var currentPage = 0

    /**
     * 获取积分记录分页列表
     *
     * @param isRefresh 是否是下拉刷新
     */
    fun fetchIntegralRecordPageList(isRefresh: Boolean = true) {
        emitUiState(isRefresh, integralRecordList)
        launch({
            if (isRefresh) {
                integralRecordList.clear()
                currentPage = 0
            }

            handleRequest(
                DataRepository.getIntegralRecordPageList(
                    currentPage++,
                    PAGE_SIZE
                )
            ) {
                integralRecordList.addAll(it.data.datas)
                if (it.data.over) {
                    emitUiState(
                        data =
                        integralRecordList, showLoadingMore = false, noMoreData = true
                    )
                    return@handleRequest
                }
                currentPage++

                emitUiState(data = integralRecordList, showLoadingMore = true)
            }
        })
    }
}