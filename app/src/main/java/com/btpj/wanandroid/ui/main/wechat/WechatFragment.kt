package com.btpj.wanandroid.ui.main.wechat

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.lib_base.ext.toHtml
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.Classify
import com.btpj.wanandroid.data.bean.ProjectTitle
import com.btpj.wanandroid.databinding.FragmentViewpagerBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * 公众号Tab
 *
 * @author LTP 2022/3/10
 */
class WechatFragment :
    BaseVMBFragment<WechatViewModel, FragmentViewpagerBinding>(R.layout.fragment_viewpager) {

    /** TabLayout的标题集合 */
    private val mAuthorTitleList = ArrayList<Classify>()

    private lateinit var mTabLayoutMediator: TabLayoutMediator

    private lateinit var mFragmentStateAdapter: FragmentStateAdapter

    companion object {
        fun newInstance() = WechatFragment()
    }

    override fun initView() {
        mFragmentStateAdapter = object : FragmentStateAdapter(parentFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return mAuthorTitleList.size
            }

            override fun createFragment(position: Int): Fragment {
               return WechatChildFragment.newInstance(authorId = mAuthorTitleList[position].id)
            }
        }

        mBinding.apply {
            viewPager2.apply {
                adapter = mFragmentStateAdapter
            }

            mTabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = mAuthorTitleList[position].name
            }.apply { attach() }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserve() {
        super.createObserve()
        mViewModel.apply {
            authorTitleListLiveData.observe(viewLifecycleOwner) { list ->
                mAuthorTitleList.apply {
                    clear()
                    addAll(list)
                }

                mFragmentStateAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTabLayoutMediator.detach()
    }
}