package com.btpj.wanandroid.ui.splash

import androidx.lifecycle.MutableLiveData
import com.btpj.wanandroid.base.BaseViewModel
import com.btpj.wanandroid.data.local.CacheManager

/**
 * @author LTP  2022/4/21
 */
class SplashViewModel : BaseViewModel<Nothing>() {

    val isFirstUse = MutableLiveData(false)

    fun start() {
        isFirstUse.value = CacheManager.isFirstUse()
    }
}