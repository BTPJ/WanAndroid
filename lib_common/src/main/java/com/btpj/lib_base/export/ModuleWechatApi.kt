package com.btpj.lib_base.export

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter

/**
 * module_wechat模块对外提供的接口管理类
 * 包括：页面跳转、获取实例等
 *
 * @author LTP  2022/5/11
 */
object ModuleWechatApi {

    const val ROUTER_WECHAT_WECHAT_FRAGMENT = "/wechat/WechatFragment"

    /**
     * 获取HomeFragment实例
     * @return  HomeFragment实例
     */
    fun getWechatFragment(): Fragment {
        return ARouter.getInstance().build(ROUTER_WECHAT_WECHAT_FRAGMENT).navigation() as Fragment
    }
}