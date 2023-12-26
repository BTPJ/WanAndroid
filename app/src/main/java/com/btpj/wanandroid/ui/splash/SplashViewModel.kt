package com.btpj.wanandroid.ui.splash

import androidx.databinding.ObservableBoolean
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.wanandroid.data.local.CacheManager

/**
 * @author LTP  2022/4/21
 */
class SplashViewModel : BaseViewModel<Nothing>() {

    val isFirstUse = ObservableBoolean(false)

    fun start() {
        isFirstUse.set(CacheManager.isFirstUse())
    }
}