package com.btpj.lib_base.base

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.data.bean.CollectData
import com.btpj.lib_base.data.bean.User

/**
 * App全局ViewModel可直接替代EventBus
 *
 * @author LTP  2022/4/13
 */
class AppViewModel : BaseViewModel() {
    override fun start() {}

    /** 全局用户 */
    val userEvent = MutableLiveData<User?>()

    /** 分享添加文章 */
    val shareArticleEvent = MutableLiveData<Boolean>()

    /** 文章收藏 */
    val collectEvent = MutableLiveData<CollectData>()
}