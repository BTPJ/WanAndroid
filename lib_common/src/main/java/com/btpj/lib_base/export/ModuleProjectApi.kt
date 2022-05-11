package com.btpj.lib_base.export

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter

/**
 * module_project模块对外提供的接口管理类
 * 包括：页面跳转、获取实例等
 *
 * @author LTP  2022/5/11
 */
object ModuleProjectApi {

    const val ROUTER_PROJECT_PROJECT_FRAGMENT = "/project/ProjectFragment"

    /**
     * 获取HomeFragment实例
     * @return  HomeFragment实例
     */
    fun getProjectFragment(): Fragment {
        return ARouter.getInstance().build(ROUTER_PROJECT_PROJECT_FRAGMENT).navigation() as Fragment
    }
}