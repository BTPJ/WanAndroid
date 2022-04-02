package com.btpj.wanandroid.ui.main.project

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.bean.PageResponse
import com.btpj.lib_base.ext.handleResponse
import com.btpj.lib_base.ext.launch
import com.btpj.lib_base.utils.LogUtil
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.ProjectTitle

class ProjectChildViewModel : BaseViewModel() {

    /** 项目分页列表LiveData */
    val articlePageListLiveData = MutableLiveData<PageResponse<Article>>()

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    override fun start() {}

    /** 请求最新项目分页列表 */
    fun fetchNewProjectPageList(pageNo: Int = 0) {
        launch({
            DataRepository.getNewProjectPageList(pageNo, PAGE_SIZE).let {
                handleResponse(it, { articlePageListLiveData.value = it.data })
            }
        })
    }

    /** 请求项目分页列表 */
    fun fetchProjectPageList(pageNo: Int = 1, categoryId: Int) {
        launch({
            DataRepository.getProjectPageList(pageNo, PAGE_SIZE, categoryId).let {
                handleResponse(it, { articlePageListLiveData.value = it.data })
            }
        })
    }
}