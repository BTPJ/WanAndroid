package com.btpj.wanandroid.ui.main.project

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.btpj.lib_base.ext.toHtml
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.BaseFragment
import com.btpj.wanandroid.data.bean.ProjectTitle
import com.btpj.wanandroid.databinding.FragmentViewpagerBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * 项目Tab
 *
 * @author LTP 2022/3/10
 */
class ProjectFragment :
    BaseFragment<ProjectViewModel, FragmentViewpagerBinding>(R.layout.fragment_viewpager) {

    /** TabLayout的标题集合 */
    private val mProjectTitleList = ArrayList<ProjectTitle>()
    private val mTitleList = ArrayList<String>()

    private lateinit var mTabLayoutMediator: TabLayoutMediator

    private lateinit var mFragmentStateAdapter: FragmentStateAdapter

    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun initView() {
        mFragmentStateAdapter = object : FragmentStateAdapter(parentFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return mTitleList.size
            }

            override fun createFragment(position: Int): Fragment {
                return if (position == 0) ProjectChildFragment.newInstance(true)
                else ProjectChildFragment.newInstance(categoryId = mProjectTitleList[position - 1].id)
            }
        }

        mBinding.apply {
            // 由于标题也需要请求（只有请求完标题后才会加载Fragment从而显示swipeRefreshLayout），
            // 所以在请求标题之前也需要一个loading
            showLoading = true

            viewPager2.apply {
                adapter = mFragmentStateAdapter
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

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserve() {
        super.createObserve()
        mViewModel.apply {
            projectTitleListLiveData.observe(viewLifecycleOwner) { list ->
                mBinding.showLoading = false
                mProjectTitleList.apply {
                    clear()
                    addAll(list)
                }

                mTitleList.apply {
                    clear()
                    mTitleList.add("最新项目")
                }
                list.forEach { projectTitle ->
                    mTitleList.add(projectTitle.name.toHtml().toString())
                }
                mFragmentStateAdapter.notifyDataSetChanged()
                // 这里的方案是直接缓存所有子Fragment然后让子Fragment懒加载数据体验更好
                mBinding.viewPager2.offscreenPageLimit = mTitleList.size
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTabLayoutMediator.detach()
    }
}