package com.btpj.module_mine.ui.mine

import android.annotation.SuppressLint
import com.alibaba.android.arouter.facade.annotation.Route
import com.btpj.lib_base.base.App
import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.lib_base.data.bean.Banner
import com.btpj.lib_base.data.local.UserManager
import com.btpj.lib_base.export.ModuleMineApi
import com.btpj.lib_base.export.ModuleWebApi
import com.btpj.lib_base.ext.initColors
import com.btpj.lib_base.ext.launchCheckLogin
import com.btpj.module_mine.R
import com.btpj.module_mine.databinding.MineFragmentMineBinding
import com.btpj.module_mine.ui.collect.CollectActivity
import com.btpj.module_mine.ui.integral.rank.IntegralRankActivity
import com.btpj.module_mine.ui.setting.SettingActivity
import com.btpj.module_mine.ui.share.list.MyArticleActivity

/**
 * 我的Tab
 *
 * @author LTP 2022/3/10
 */
@Route(path = ModuleMineApi.ROUTER_MINE_MINE_FRAGMENT)
class MineFragment :
    BaseVMBFragment<MineViewModel, MineFragmentMineBinding>(R.layout.mine_fragment_mine) {

    override fun initView() {
        mBinding.apply {
            swipeRefreshLayout.apply {
                initColors()
                setOnRefreshListener { onRefresh() }
            }

            clUser.setOnClickListener {
                if (!UserManager.isLogin()) {
                    ModuleMineApi.navToLoginActivity()
                }
            }

            // 我的积分
            tvPoints.setOnClickListener {
                IntegralRankActivity.launch(requireContext())
            }

            // 我的收藏（需要登录）
            tvCollect.setOnClickListener {
                requireContext().launchCheckLogin { CollectActivity.launch(it) }
            }

            // 我分享的文章（需要登录）
            tvArticle.setOnClickListener {
                requireContext().launchCheckLogin { MyArticleActivity.launch(it) }
            }

            // 开源网站
            tvWeb.setOnClickListener {
                ModuleWebApi.navToWebActivity(
                    Banner(
                        title = "玩Android网站",
                        url = "https://www.wanandroid.com/"
                    )
                )
            }

            // 设置
            tvSettings.setOnClickListener { SettingActivity.launch(requireContext()) }
        }

        onRefresh()
    }

    /** 下拉刷新 */
    private fun onRefresh() {
        if (UserManager.isLogin()) {
            mBinding.swipeRefreshLayout.isEnabled = true
            mBinding.swipeRefreshLayout.isRefreshing = true
            mViewModel.fetchPoints()
        } else {
            mBinding.swipeRefreshLayout.isEnabled = false
        }
    }

    @SuppressLint("SetTextI18n")
    override fun createObserve() {
        super.createObserve()
        App.appViewModel.userEvent.observe(this) {
            mViewModel.user.set(it)
            if (it == null) {
                mViewModel.integral.value = null
            }
            onRefresh()
        }

        mViewModel.integral.observe(this) {
            mBinding.apply {
                swipeRefreshLayout.isRefreshing = false
                tvInfo.text = "id：${it?.userId ?: '—'}　排名：${it?.rank ?: '—'}"
                tvPointsNum.text = "${it?.coinCount ?: '—'}"
            }
        }
    }

    override fun requestError(msg: String?) {
        super.requestError(msg)
        mBinding.swipeRefreshLayout.isRefreshing = false
    }
}