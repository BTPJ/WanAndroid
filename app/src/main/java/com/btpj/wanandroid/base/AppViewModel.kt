package com.btpj.wanandroid.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseAppViewModel
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.data.bean.ApiResponse
import com.btpj.wanandroid.data.bean.CollectData
import com.btpj.wanandroid.data.bean.User
import com.btpj.wanandroid.data.local.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * App全局ViewModel可直接替代EventBus
 *
 * @author LTP  2022/4/13
 */
class AppViewModel : BaseAppViewModel() {

    /** 全局用户 */
    private val _user = MutableStateFlow(UserManager.getUser())
    val user: StateFlow<User?> = _user

    /** 分享添加文章 */
    private val _shareArticleEvent = MutableLiveData<Boolean>()
    val shareArticleEvent: LiveData<Boolean> = _shareArticleEvent

    /** 文章收藏 */
    val collectEvent = MutableLiveData<CollectData>()

    /** emit全局用户 */
    fun emitUser(user: User?) {
        _user.value = user
    }

    /** 发送分享文章成功的消息 */
    fun emitShareArticleSuccess() {
        _shareArticleEvent.value = true
    }
}