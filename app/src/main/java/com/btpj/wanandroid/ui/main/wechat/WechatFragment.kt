package com.btpj.wanandroid.ui.main.wechat

import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.wanandroid.R
import com.btpj.wanandroid.databinding.FragmentWechatBinding

/**
 * 公众号Tab
 *
 * @author LTP 2022/3/10
 */
class WechatFragment :
    BaseVMBFragment<WechatViewModel, FragmentWechatBinding>(R.layout.fragment_wechat) {

    companion object {
        fun newInstance() = WechatFragment()
    }

    override fun initView() {

    }
}