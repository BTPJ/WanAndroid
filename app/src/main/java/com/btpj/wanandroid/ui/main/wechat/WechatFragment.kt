package com.btpj.wanandroid.ui.main.wechat

import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.wanandroid.R
import com.btpj.wanandroid.databinding.HomeFragmentBinding

/**
 * 我的
 *
 * @author LTP 2022/3/10
 */
class WechatFragment :
    BaseVMBFragment<WechatViewModel, HomeFragmentBinding>(R.layout.wechat_fragment) {

    companion object {
        fun newInstance() = WechatFragment()
    }

    override fun initView() {

    }
}