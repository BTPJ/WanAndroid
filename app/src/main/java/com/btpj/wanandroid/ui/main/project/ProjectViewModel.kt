package com.btpj.wanandroid.ui.main.project

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.ProjectTitle

/**
 * @author LTP  2023/12/19
 */
class ProjectViewModel : BaseViewModel<Nothing>() {

    /** 项目标题列表LiveData */
    private val _projectTitleListLiveData = MutableLiveData<List<ProjectTitle>?>()
    val projectTitleListLiveData = _projectTitleListLiveData

    /** 请求项目标题列表 */
    fun fetchProjectTitleList() {
        launch({
            handleRequest(
                DataRepository.getProjectTitleList()
            )
            {
                val list = ArrayList<ProjectTitle>()
                list.add(
                    ProjectTitle(
                        author = "",
                        children = ArrayList(),
                        courseId = 0,
                        cover = "",
                        desc = "",
                        id = -1,
                        lisense = "",
                        lisenseLink = "",
                        name = "最新项目",
                        order = 0,
                        parentChapterId = 0,
                        userControlSetTop = true,
                        visible = 0
                    )
                )
                list.addAll(it.data)
                projectTitleListLiveData.value = list
            }
        })
    }
}