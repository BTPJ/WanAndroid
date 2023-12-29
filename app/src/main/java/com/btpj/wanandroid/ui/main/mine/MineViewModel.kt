package com.btpj.wanandroid.ui.main.mine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.CoinInfo
import com.btpj.wanandroid.data.bean.User

class MineViewModel : BaseViewModel<User>() {
    //    val user = ObservableField<User?>()
    private val _integral = MutableLiveData<CoinInfo?>()
    val integral: LiveData<CoinInfo?> = _integral

//    val userName = object : ObservableField<String>(user) {
//        override fun get(): String {
//            return if (UserManager.isLogin()) user.get()!!.nickname else "请登录"
//        }
//    }

//    fun start() {
//        if (UserManager.isLogin()) {
//            user.set(UserManager.getUser())
//        }
//    }

    /** 获取个人积分 */
    fun fetchPoints() {
        launch({
            val response = DataRepository.getUserIntegral()
            handleRequest(response) {
                _integral.value = response.data
            }
        })
    }
}