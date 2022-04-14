package com.btpj.wanandroid.ui.integral.rank

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.bean.PageResponse
import com.btpj.lib_base.ext.handleResponse
import com.btpj.lib_base.ext.request
import com.btpj.lib_base.utils.LogUtil
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Integral
import com.btpj.wanandroid.data.local.UserManager

class IntegralRankViewModel : BaseViewModel() {
    /** 我的积分信息 */
    val myIntegral = ObservableField<Integral>()
    val integralPageList = MutableLiveData<PageResponse<Integral>>()

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
        request({
            val response = DataRepository.getIntegralRankPageList(pageNo)
            handleResponse(response, {
                integralPageList.value = response.data!!
            })
        })
    }

    /** 获取个人积分 */
    private fun fetchPoints() {
        request({
            val response = DataRepository.getUserIntegral()
            handleResponse(response, {
                myIntegral.set(response.data)
                LogUtil.d(response.data.toString())
            })
        })
    }
}