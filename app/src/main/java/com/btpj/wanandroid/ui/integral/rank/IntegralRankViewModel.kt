package com.btpj.wanandroid.ui.integral.rank

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.CoinInfo
import com.btpj.wanandroid.data.local.UserManager

class IntegralRankViewModel : BaseViewModel() {
    /** 我的积分信息 */
    val myIntegral = ObservableField<CoinInfo>()
    val integralPageList = MutableLiveData<PageResponse<CoinInfo>>()

    override fun start() {
        if (UserManager.isLogin()) {
            fetchPoints()
        }
    }

    /**
     * 获取积分排行分页列表
     *
     * @param pageNo 页码
     */
    fun fetchIntegralRankList(pageNo: Int = 1) {
        launch({
            handleRequest(DataRepository.getIntegralRankPageList(pageNo), {
                integralPageList.value = it.data!!
            })
        })
    }

    /** 获取个人积分 */
    private fun fetchPoints() {
        launch({
            handleRequest(DataRepository.getUserIntegral(), {
                myIntegral.set(it.data)
            })
        })
    }
}