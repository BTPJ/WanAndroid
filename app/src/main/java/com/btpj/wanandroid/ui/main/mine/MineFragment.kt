package com.btpj.wanandroid.ui.main.mine

import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.wanandroid.R
import com.btpj.wanandroid.databinding.FragmentMineBinding

/**
 * 我的
 *
 * @author LTP 2022/3/10
 */
class MineFragment : BaseVMBFragment<MineViewModel, FragmentMineBinding>(R.layout.fragment_mine) {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun initView() {

    }
}