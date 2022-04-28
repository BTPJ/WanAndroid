package com.btpj.wanandroid.ui.login.register

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.local.UserManager

/**
 * @author LTP  2022/3/9
 */
class RegisterViewModel : BaseViewModel() {

    val userName = ObservableField("")
    val userPwd = ObservableField("")
    val userPwdSure = ObservableField("")

    /** 注册按键是否可点击(这样可避免在dataBinding中写较复杂的逻辑) */
    val loginBtnEnable = object : ObservableBoolean(userName, userPwd, userPwdSure) {
        override fun get(): Boolean {
            return !userName.get()?.trim().isNullOrEmpty() && !userPwd.get()
                .isNullOrEmpty() && !userPwdSure.get().isNullOrEmpty()
        }
    }

    override fun start() {}

    /**
     * 注册
     * @param userName 用户名
     * @param pwd 密码
     * @param pwdSure 确认密码
     * @param successCall 成功回调函数
     */
    fun register(userName: String, pwd: String, pwdSure: String, successCall: () -> Any? = {}) {
        launch({
            handleRequest(DataRepository.register(userName, pwd, pwdSure), successBlock = {
                UserManager.saveLastUserName(userName)
                successCall.invoke()
            })
        })
    }
}