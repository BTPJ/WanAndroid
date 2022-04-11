package com.btpj.wanandroid.ui.main.mine

import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.lib_base.utils.LogUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.local.UserManager
import com.btpj.wanandroid.databinding.FragmentMineBinding
import com.btpj.wanandroid.ui.login.LoginActivity
import com.btpj.wanandroid.ui.main.setting.SettingActivity
import com.btpj.wanandroid.ui.web.WebActivity

/**
 * 我的Tab
 *
 * @author LTP 2022/3/10
 */
class MineFragment : BaseVMBFragment<MineViewModel, FragmentMineBinding>(R.layout.fragment_mine) {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun initView() {
        mBinding.apply {
            clUser.setOnClickListener {
                if (!UserManager.isLogin()) {
                    LoginActivity.launch(requireContext())
                }
            }

            tvWeb.setOnClickListener {
                WebActivity.launch(requireContext(), "https://www.wanandroid.com/")
            }

            tvSettings.setOnClickListener { SettingActivity.launch(requireContext()) }
        }
    }

    override fun createObserve() {
        super.createObserve()
        UserManager.getUserLiveData().observe(this) {
            LogUtil.d(it?.nickname?:"")
            mViewModel.user.set(it)
        }
    }
}