package com.btpj.wanandroid.ui.collect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.BaseActivity
import com.btpj.wanandroid.databinding.ActivityCollectBinding
import com.btpj.wanandroid.ui.collect.article.CollectArticleFragment
import com.btpj.wanandroid.ui.collect.url.CollectUrlFragment
import com.google.android.material.tabs.TabLayoutMediator

/**
 * 收藏页面
 *
 * @author LTP 2022/4/13
 */
class CollectActivity :
    BaseActivity<CollectViewModel, ActivityCollectBinding>(R.layout.activity_collect) {

    /** 标题 */
    private val mTitleList = arrayListOf("文章", "网址")
    private val mFragmentList: ArrayList<Fragment> = ArrayList()

    private lateinit var mTabLayoutMediator: TabLayoutMediator
    private lateinit var mFragmentStateAdapter: FragmentStateAdapter

    init {
        mFragmentList.add(CollectArticleFragment.newInstance())
        mFragmentList.add(CollectUrlFragment.newInstance())
    }

    companion object {

        /**
         * 页面启动
         * @param context Context
         */
        fun launch(context: Context) {
            context.startActivity(Intent(context, CollectActivity::class.java))
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mFragmentStateAdapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
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