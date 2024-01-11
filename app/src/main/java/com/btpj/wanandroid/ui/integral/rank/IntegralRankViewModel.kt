package com.btpj.wanandroid.ui.integral.rank

import com.btpj.wanandroid.base.BaseViewModel
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.CoinInfo
import com.btpj.wanandroid.data.local.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class IntegralRankViewModel : BaseViewModel<List<CoinInfo>>() {

    /** 我的积分信息 */
    private val _myCoinInfo = MutableStateFlow<CoinInfo?>(null)
    val myCoinInfo: StateFlow<CoinInfo?> = _myCoinInfo

    companion object {
        /** 每页显示的条目大小 */
        private const val PAGE_SIZE = 10
    }

    private val coinInfoList = ArrayList<CoinInfo>()
    private var currentPage = 1

    /**
     * 获取积分排行分页列表
     *
     * @param isRefresh 是否是下拉刷新
     */
    fun fetchIntegralRankList(isRefresh: Boolean = true) {
        emitUiState(isRefresh, coinInfoList)
        launch({
            if (isRefresh) {
                coinInfoList.clear()
                currentPage = 1
            }

            handleRequest(
                DataRepository.getIntegralRankPageList(
                    currentPage++,
                    PAGE_SIZE
                )
            ) {
                coinInfoList.addAll(it.data.datas)
                if (!it.data.over) {
                    emitUiState(
                        data =
                        coinInfoList, showLoadingMore = false, noMoreData = true
                    )
                    return@handleRequest
                }
                currentPage++

                emitUiState(data = coinInfoList, showLoadingMore = true)
            }
        })
    }

    /** 获取个人积分 */
    fun fetchMyCoinInfo() {
        //  未登录时无需请求
        if (!UserManager.isLogin()) return

        launch({
            handleRequest(DataRepository.getUserIntegral()) {
                _myCoinInfo.value = it.data
            }
        })
    }
}