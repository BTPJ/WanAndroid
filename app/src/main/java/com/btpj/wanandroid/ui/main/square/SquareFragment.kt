package com.btpj.wanandroid.ui.main.square

import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.wanandroid.R
import com.btpj.wanandroid.databinding.HomeFragmentBinding

/**
 * 我的
 *
 * @author LTP 2022/3/10
 */
class SquareFragment :
    BaseVMBFragment<SquareViewModel, HomeFragmentBinding>(R.layout.square_fragment) {

    companion object {
        fun newInstance() = SquareFragment()
    }

    override fun initView() {

    }
}