package com.btpj.wanandroid.ui.main.mine

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleResponse
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Integral
import com.btpj.wanandroid.data.bean.User
import com.btpj.wanandroid.data.local.UserManager

class MineViewModel : BaseViewModel() {
    val user = ObservableField<User?>()
    val integral = MutableLiveData<Integral?>()

    val userName = object : ObservableField<String>(user) {
        override fun get(): String {
            return if (UserManager.isLogin()) user.get()!!.nickname else "请登录"
        }
    }

    override fun start() {
        if (UserManager.isLogin()) {
            user.set(UserManager.getUser())
        }
    }

    /** 获取个人积分 */
    fun fetchPoints() {
        launch({
            val response = DataRepository.getUserIntegral()
            handleResponse(response, {
                integral.value = response.data
            })
        })
    }
}