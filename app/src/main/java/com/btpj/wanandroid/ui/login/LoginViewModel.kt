package com.btpj.wanandroid.ui.login

import com.btpj.wanandroid.App
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.local.UserManager

/**
 * @author LTP  2022/3/9
 */
class LoginViewModel : BaseViewModel<String>() {

    /**
     * 登录
     * @param userName 用户名
     * @param pwd 密码
     */
    fun login(
        userName: String,
        pwd: String,
        errorBlock: () -> Unit = {},
        successCall: () -> Unit = {}
    ) {
        launch({
            handleRequest(DataRepository.login(userName, pwd), errorBlock = {
                errorBlock()
                false
            }) {
                UserManager.saveLastUserName(userName)
                UserManager.saveUser(it.data)
                App.appViewModel.emitUser(it.data)
                successCall.invoke()
            }
        })
    }
}