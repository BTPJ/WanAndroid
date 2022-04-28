package com.btpj.wanandroid.ui.main.mine

import android.annotation.SuppressLint
import com.btpj.lib_base.ext.initColors
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.App
import com.btpj.wanandroid.base.BaseFragment
import com.btpj.wanandroid.data.bean.Banner
import com.btpj.wanandroid.data.local.UserManager
import com.btpj.wanandroid.databinding.FragmentMineBinding
import com.btpj.wanandroid.ext.launchCheckLogin
import com.btpj.wanandroid.ui.collect.CollectActivity
import com.btpj.wanandroid.ui.integral.rank.IntegralRankActivity
import com.btpj.wanandroid.ui.login.LoginActivity
import com.btpj.wanandroid.ui.setting.SettingActivity
import com.btpj.wanandroid.ui.share.list.MyArticleActivity
import com.btpj.wanandroid.ui.web.WebActivity

/**
 * 我的Tab
 *
 * @author LTP 2022/3/10
 */
class MineFragment : BaseFragment<MineViewModel, FragmentMineBinding>(R.layout.fragment_mine) {

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
                WebActivity.launch(
                    requireContext(),
                    Banner(title = "玩Android网站", url = "https://www.wanandroid.com/")
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