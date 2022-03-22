package com.btpj.wanandroid.ui.main.home

import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.wanandroid.R
import com.btpj.wanandroid.databinding.FragmentHomeBinding

/**
 * 首页
 *
 * @author LTP 2022/3/10
 */
class HomeFragment : BaseVMBFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun initView() {

    }
}