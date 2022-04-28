package com.btpj.wanandroid.ui.main.square

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.btpj.lib_base.utils.ScreenUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.BaseFragment
import com.btpj.wanandroid.databinding.FragmentViewpagerBinding
import com.btpj.wanandroid.ext.launchCheckLogin
import com.btpj.wanandroid.ui.main.square.ask.AskFragment
import com.btpj.wanandroid.ui.main.square.navigation.NavigationFragment
import com.btpj.wanandroid.ui.main.square.square.SquareChildFragment
import com.btpj.wanandroid.ui.main.square.system.SystemFragment
import com.btpj.wanandroid.ui.share.add.AddArticleActivity
import com.google.android.material.tabs.TabLayoutMediator

/**
 * 广场Tab
 *
 * @author LTP 2022/3/10
 */
class SquareFragment :
    BaseFragment<SquareViewModel, FragmentViewpagerBinding>(R.layout.fragment_viewpager) {

    /** 标题 */
    private val mTitleList = arrayListOf("广场", "每日一问", "体系", "导航")
    private val mFragmentList: ArrayList<Fragment> = ArrayList()

    private lateinit var mTabLayoutMediator: TabLayoutMediator
    private lateinit var mFragmentStateAdapter: FragmentStateAdapter

    private val mPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position == 0) {
                mBinding.titleLayout.setRightView(R.drawable.ic_add) {
                    requireContext().launchCheckLogin { AddArticleActivity.launch(it) }
                }
            } else {
                mBinding.titleLayout.setRightView("")
            }
        }
    }

    init {
        mFragmentList.add(SquareChildFragment.newInstance())
        mFragmentList.add(AskFragment.newInstance())
        mFragmentList.add(SystemFragment.newInstance())
        mFragmentList.add(NavigationFragment.newInstance())
    }

    companion object {
        fun newInstance() = SquareFragment()
    }

    override fun initView() {
        mFragmentStateAdapter = object : FragmentStateAdapter(parentFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return mTitleList.size
            }

            override fun createFragment(position: Int): Fragment {
                return mFragmentList[position]
            }
        }

        mBinding.apply {
            tabLayout.layoutParams =
                    // 不想重新写布局...
                ConstraintLayout.LayoutParams(tabLayout.layoutParams).apply {
                    marginStart = ScreenUtil.dp2px(requireContext(), 40f)
                    marginEnd = ScreenUtil.dp2px(requireContext(), 60f)
                    topToTop = mBinding.root.top
                    topMargin = ScreenUtil.dp2px(requireContext(), 6f)
                }

            viewPager2.apply {
                adapter = mFragmentStateAdapter
                offscreenPageLimit = mFragmentList.size
                registerOnPageChangeCallback(mPageChangeCallback)
            }

            mTabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.apply {
                    // 处理长按出现toast的问题
                    view.setOnLongClickListener { true }
                    text = mTitleList[position]
                }
            }.apply { attach() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTabLayoutMediator.detach()
        mBinding.viewPager2.unregisterOnPageChangeCallback(mPageChangeCallback)
    }
}