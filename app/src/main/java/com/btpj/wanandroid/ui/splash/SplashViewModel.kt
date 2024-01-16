package com.btpj.wanandroid.ui.splash

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.wanandroid.data.local.CacheManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author LTP  2022/4/21
 */
class SplashViewModel : BaseViewModel<Unit>() {

    private val _isFirstUse = MutableStateFlow(CacheManager.isFirstUse())
    val isFirstUse: StateFlow<Boolean> = _isFirstUse

    fun emitFirstUse(firstUse: Boolean) {
        _isFirstUse.value = firstUse
        CacheManager.saveFirstUse(firstUse)
    }
}