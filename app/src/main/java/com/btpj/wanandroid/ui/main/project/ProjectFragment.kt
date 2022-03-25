package com.btpj.wanandroid.ui.main.project

import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.wanandroid.R
import com.btpj.wanandroid.databinding.FragmentProjectBinding

/**
 * 项目Tab
 *
 * @author LTP 2022/3/10
 */
class ProjectFragment :
    BaseVMBFragment<ProjectViewModel, FragmentProjectBinding>(R.layout.fragment_project) {

    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun initView() {

    }
}