package com.btpj.module_project.ui.project

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.lib_base.data.bean.ProjectTitle
import com.btpj.module_project.data.DataRepository

class ProjectViewModel : BaseViewModel() {

    /** 项目标题列表LiveData */
    val projectTitleListLiveData = MutableLiveData<List<ProjectTitle>>()

    override fun start() {
        fetchProjectTitleList()
    }

    /** 请求项目标题列表 */
    private fun fetchProjectTitleList() {
        launch({
            handleRequest(
                DataRepository.getProjectTitleList(),
                { projectTitleListLiveData.value = it.data })
        })
    }
}