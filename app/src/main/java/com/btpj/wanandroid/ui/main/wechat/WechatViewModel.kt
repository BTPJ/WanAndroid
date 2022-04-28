package com.btpj.wanandroid.ui.main.wechat

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Classify

class WechatViewModel : BaseViewModel() {

    /** 项目标题列表LiveData */
    val authorTitleListLiveData = MutableLiveData<List<Classify>>()

    override fun start() {
        fetchAuthorTitleList()
    }

    /** 请求公众号作者标题列表 */
    private fun fetchAuthorTitleList() {
        launch({
            handleRequest(
                DataRepository.getAuthorTitleList(),
                { authorTitleListLiveData.value = it.data })
        })
    }
}