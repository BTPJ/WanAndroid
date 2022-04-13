package com.btpj.wanandroid.ui.main.mine

import android.annotation.SuppressLint
import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.lib_base.ext.initColors
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.local.UserManager
import com.btpj.wanandroid.databinding.FragmentMineBinding
import com.btpj.wanandroid.ext.launchCheckLogin
import com.btpj.wanandroid.ui.collect.CollectActivity
import com.btpj.wanandroid.ui.integral.rank.IntegralRankActivity
import com.btpj.wanandroid.ui.login.LoginActivity
import com.btpj.wanandroid.ui.setting.SettingActivity
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
            swipeRefreshLayout.apply {
                initColors()
                setOnRefreshListener { onRefresh() }
            }

            clUser.setOnClickListener {
                if (!UserManager.isLogin()) {
                    LoginActivity.launch(requireContext())
                }
            }

            // 我的积分
            tvPoints.setOnClickListener {
                IntegralRankActivity.launch(requireContext())
            }

            // 我的收藏
            tvCollect.setOnClickListener {
                requireContext().launchCheckLogin { CollectActivity.launch(it) }
            }

            // 开源网站
            tvWeb.setOnClickListener {
                WebActivity.launch(requireContext(), "https://www.wanandroid.com/")
            }

            // 设置
            tvSettings.setOnClickListener { SettingActivity.launch(requireContext()) }
        }

        onRefresh()
    }

    /** 下拉刷新 */
    private fun onRefresh() {
        mBinding.swipeRefreshLayout.isRefreshing = true
        mViewModel.fetchPoints()
    }

    @SuppressLint("SetTextI18n")
    override fun createObserve() {
        super.createObserve()
        UserManager.getUserLiveData().observe(this) {
            mViewModel.user.set(it)
            if (it == null) {
                mViewModel.integral.value = null
            } else {
                onRefresh()
            }
        }

        mViewModel.integral.observe(this) {
            mBinding.apply {
                swipeRefreshLayout.isRefreshing = false
                tvInfo.text = "id：${it?.userId ?: '—'}　排名：${it?.rank ?: '—'}"
                tvPointsNum.text = "${it?.coinCount ?: '—'}"
            }
        }
    }
}