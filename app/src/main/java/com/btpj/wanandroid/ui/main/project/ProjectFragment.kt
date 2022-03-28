package com.btpj.wanandroid.ui.main.project

import android.annotation.SuppressLint
import android.text.Html
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.lib_base.ext.toHtml
import com.btpj.lib_base.utils.LogUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.ProjectTitle
import com.btpj.wanandroid.databinding.FragmentProjectBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * 项目Tab
 *
 * @author LTP 2022/3/10
 */
class ProjectFragment :
    BaseVMBFragment<ProjectViewModel, FragmentProjectBinding>(R.layout.fragment_project) {

    /** TabLayout的标题集合 */
    private val mProjectTitleList = ArrayList<ProjectTitle>()

    private lateinit var mTabLayoutMediator: TabLayoutMediator

    private lateinit var mFragmentStateAdapter: FragmentStateAdapter

    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun initView() {
        mFragmentStateAdapter = object : FragmentStateAdapter(parentFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return mProjectTitleList.size
            }

            override fun createFragment(position: Int): Fragment {
                return ProjectChildFragment.newInstance(mProjectTitleList[position].id)
            }
        }

        mBinding.apply {
            viewPager2.apply {
                adapter = mFragmentStateAdapter
            }

            mTabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = mProjectTitleList[position].name.toHtml()
            }.apply { attach() }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserve() {
        super.createObserve()
        mViewModel.apply {
            projectTitleListLiveData.observe(viewLifecycleOwner) {
                mProjectTitleList.clear()
                mProjectTitleList.addAll(it)
                mFragmentStateAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTabLayoutMediator.detach()
    }
}