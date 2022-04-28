package com.btpj.wanandroid.ui.main.wechat

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.BaseFragment
import com.btpj.wanandroid.data.bean.Classify
import com.btpj.wanandroid.databinding.FragmentViewpagerBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * 公众号Tab
 *
 * @author LTP 2022/3/10
 */
class WechatFragment :
    BaseFragment<WechatViewModel, FragmentViewpagerBinding>(R.layout.fragment_viewpager) {

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
                    text = mAuthorTitleList[position].name
                }
            }.apply { attach() }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserve() {
        super.createObserve()
        mViewModel.apply {
            authorTitleListLiveData.observe(viewLifecycleOwner) { list ->
                mBinding.showLoading = false
                mAuthorTitleList.apply {
                    clear()
                    addAll(list)
                }

                mFragmentStateAdapter.notifyDataSetChanged()
                // 这里的方案是直接缓存所有子Fragment然后让子Fragment懒加载数据体验更好
                mBinding.viewPager2.offscreenPageLimit = mAuthorTitleList.size
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTabLayoutMediator.detach()
    }
}