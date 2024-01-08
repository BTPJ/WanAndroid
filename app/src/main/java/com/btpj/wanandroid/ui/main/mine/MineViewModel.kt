package com.btpj.wanandroid.ui.main.mine

import com.btpj.wanandroid.App
import com.btpj.wanandroid.base.BaseViewModel
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.CoinInfo

class MineViewModel : BaseViewModel<CoinInfo>() {

    /** 获取个人积分 */
    fun fetchPoints() {
        if (App.appViewModel.user.value == null) {
            emitUiState(false, null)
            return
        }

        emitUiState(true)
        launch({
            val response = DataRepository.getUserIntegral()
            handleRequest(response) {
                emitUiState(false, response.data)
            }
        })
    }
}