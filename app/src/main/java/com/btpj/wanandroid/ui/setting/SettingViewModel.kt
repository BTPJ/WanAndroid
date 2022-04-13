package com.btpj.wanandroid.ui.setting

import androidx.databinding.ObservableBoolean
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.wanandroid.data.local.UserManager

/**
 * @author LTP  2022/4/11
 */
class SettingViewModel : BaseViewModel() {
    val showLogoutBtn = ObservableBoolean(false)

    override fun start() {
        showLogoutBtn.set(UserManager.isLogin())
    }
}