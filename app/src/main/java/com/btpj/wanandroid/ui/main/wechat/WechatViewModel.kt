package com.btpj.wanandroid.ui.main.wechat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Classify

class WechatViewModel : BaseViewModel<Nothing>() {

    /** 项目标题列表LiveData */
    private val _authorTitleListLiveData = MutableLiveData<List<Classify>>()
    val authorTitleListLiveData: LiveData<List<Classify>> = _authorTitleListLiveData

    /** 请求公众号作者标题列表 */
    fun fetchAuthorTitleList() {
        launch({
            handleRequest(
                DataRepository.getAuthorTitleList()
            )
            { _authorTitleListLiveData.value = it.data!! }
        })
    }
}