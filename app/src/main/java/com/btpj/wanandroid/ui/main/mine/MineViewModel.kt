package com.btpj.wanandroid.ui.main.mine

import com.btpj.wanandroid.App
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.CoinInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MineViewModel @Inject constructor() : BaseViewModel<CoinInfo>() {

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