package com.btpj.wanandroid.ui.main.mine

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.App
import com.btpj.wanandroid.base.AppViewModel
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