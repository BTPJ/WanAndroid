package com.btpj.wanandroid.navigation

import com.btpj.lib_base.base.BaseViewModel
import com.btpj.wanandroid.data.local.CacheManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * @author LTP  2022/4/21
 */
@HiltViewModel
class AppScreenViewModel @Inject constructor() : BaseViewModel<Unit>() {

    private val _isFirstUse = MutableStateFlow(CacheManager.isFirstUse())
    val isFirstUse: StateFlow<Boolean> = _isFirstUse

    fun emitFirstUse(firstUse: Boolean) {
        _isFirstUse.value = firstUse
        CacheManager.saveFirstUse(firstUse)
    }
}