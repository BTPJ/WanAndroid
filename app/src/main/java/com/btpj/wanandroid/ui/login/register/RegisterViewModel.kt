package com.btpj.wanandroid.ui.login.register

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.local.UserManager

/**
 * @author LTP  2022/3/9
 */
class RegisterViewModel : BaseViewModel<String>() {

    /**
     * 注册
     * @param userName 用户名
     * @param pwd 密码
     * @param pwdSure 确认密码
     * @param successCall 成功回调函数
     */
    fun register(
        userName: String,
        pwd: String,
        pwdSure: String,
        errorBlock: () -> Unit = {},
        successCall: () -> Unit = {}
    ) {
        launch({
            handleRequest(DataRepository.register(userName, pwd, pwdSure), errorBlock = {
                errorBlock()
                false
            }, successBlock = {
                UserManager.saveLastUserName(userName)
                successCall()
            })
        })
    }
}