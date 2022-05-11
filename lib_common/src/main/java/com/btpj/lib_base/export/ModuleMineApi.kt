package com.btpj.lib_base.export

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter

/**
 * module_mine模块对外提供的接口管理类
 * 包括：页面跳转、获取实例等
 *
 * @author LTP  2022/5/11
 */
object ModuleMineApi {

    const val ROUTER_MINE_MINE_FRAGMENT = "/mine/MineFragment"
    const val ROUTER_MINE_LOGIN_ACTIVITY = "/mine/LoginActivity"
    const val ROUTER_MINE_ADD_ARTICLE_ACTIVITY = "/mine/AddArticleActivity"

    /**
     * 获取HomeFragment实例
     * @return  HomeFragment实例
     */
    fun getMineFragment(): Fragment {
        return ARouter.getInstance().build(ROUTER_MINE_MINE_FRAGMENT).navigation() as Fragment
    }

    /** 跳转到登录页面 */
    fun navToLoginActivity() {
        ARouter.getInstance().build(ROUTER_MINE_LOGIN_ACTIVITY).navigation()
    }

    /** 跳转到添加文章页面 */
    fun navToAddArticleActivity() {
        ARouter.getInstance().build(ROUTER_MINE_ADD_ARTICLE_ACTIVITY).navigation()
    }
}