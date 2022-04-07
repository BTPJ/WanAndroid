package com.btpj.wanandroid.ui.main.square

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.wanandroid.R
import com.btpj.wanandroid.databinding.FragmentViewpagerBinding
import com.btpj.wanandroid.ui.main.square.ask.AskFragment
import com.btpj.wanandroid.ui.main.square.navigation.NavigationFragment
import com.btpj.wanandroid.ui.main.square.square.SquareChildFragment
import com.btpj.wanandroid.ui.main.square.system.SystemFragment
import com.google.android.material.tabs.TabLayoutMediator

/**
 * 广场Tab
 *
 * @author LTP 2022/3/10
 */
class SquareFragment :
    BaseVMBFragment<SquareViewModel, FragmentViewpagerBinding>(R.layout.fragment_viewpager) {

    /** 标题 */
    private val mTitleList = arrayListOf("广场", "每日一问", "体系", "导航")
    private val mFragmentList: ArrayList<Fragment> = ArrayList()

    private lateinit var mTabLayoutMediator: TabLayoutMediator
    private lateinit var mFragmentStateAdapter: FragmentStateAdapter

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
            viewPager2.apply {
                adapter = mFragmentStateAdapter
            }

            mTabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = mTitleList[position]
            }.apply { attach() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTabLayoutMediator.detach()
    }
}