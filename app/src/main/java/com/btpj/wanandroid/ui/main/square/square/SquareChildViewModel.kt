package com.btpj.wanandroid.ui.main.square.square

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.bean.PageResponse
import com.btpj.lib_base.ext.handleResponse
import com.btpj.lib_base.ext.launch
import com.btpj.lib_base.utils.LogUtil
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.ProjectTitle

class SquareChildViewModel : BaseViewModel() {

    /** 广场分页列表LiveData */
    val articlePageListLiveData = MutableLiveData<PageResponse<Article>>()

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    override fun start() {}

    /** 请求广场分页列表 */
    fun fetchSquarePageList(pageNo: Int = 0) {
        launch({
            DataRepository.getSquarePageList(pageNo, PAGE_SIZE).let {
                handleResponse(it, { articlePageListLiveData.value = it.data })
            }
        })
    }
}