package com.btpj.lib_base.export

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter

/**
 * module_home模块对外提供的接口管理类
 * 包括：页面跳转、获取实例等
 *
 * @author LTP  2022/5/11
 */
object ModuleHomeApi {

    const val ROUTER_HOME_HOME_FRAGMENT = "/home/HomeFragment"
    const val ROUTER_HOME_AUTHOR_ACTIVITY = "/home/AuthorActivity"
    const val ROUTER_HOME_EXTRA_AUTHOR_ID = "extra_author_id"

    /**
     * 获取HomeFragment实例
     * @return  HomeFragment实例
     */
    fun getHomeFragment(): Fragment {
        return ARouter.getInstance().build(ROUTER_HOME_HOME_FRAGMENT).navigation() as Fragment
    }

    /**
     * 跳转到作者文章列表页面
     * @param authorId 作者Id
     */
    fun navToAuthorActivity(authorId: Int) {
        ARouter.getInstance().build(ROUTER_HOME_AUTHOR_ACTIVITY)
            .withInt(ROUTER_HOME_EXTRA_AUTHOR_ID, authorId).navigation()
    }
}