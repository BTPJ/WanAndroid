package com.btpj.wanandroid.ui.login

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.base.App
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.local.UserManager

/**
 * @author LTP  2022/3/9
 */
class LoginViewModel : BaseViewModel() {

    val userName = ObservableField("")
    val userPwd = ObservableField("")

    /** 登录按键是否可点击(这样可避免在dataBinding中写较复杂的逻辑) */
    val loginBtnEnable = object : ObservableBoolean(userName, userPwd) {
        override fun get(): Boolean {
            return !userName.get()?.trim().isNullOrEmpty() && !userPwd.get().isNullOrEmpty()
        }
    }

    override fun start() {
        userName.set(UserManager.getLastUserName())
    }

    /**
     * 登录
     * @param userName 用户名
     * @param pwd 密码
     */
    fun login(userName: String, pwd: String, successCall: () -> Any? = {}) {
        launch({
            handleRequest(DataRepository.login(userName, pwd), successBlock = {
                UserManager.saveLastUserName(userName)
                UserManager.saveUser(it.data)
                App.appViewModel.userEvent.value = it.data
                successCall.invoke()
            })
        })
    }
}