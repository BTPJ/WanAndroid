package com.btpj.module_square.ui.square.system.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.btpj.lib_base.base.BaseVMBActivity
import com.btpj.lib_base.data.bean.MySystem
import com.btpj.module_square.R
import com.btpj.module_square.databinding.ActivitySystemArticleListBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * 体系文章详情页面
 *
 * @author LTP 2024/1/18
 */
class SystemArticleListActivity :
    BaseVMBActivity<SystemArticleListViewModel, ActivitySystemArticleListBinding>(
        R.layout.activity_system_article_list
    ) {

    private var mPageIndex = 0
    private lateinit var mySystem: MySystem
    private lateinit var mTitleList: List<String>
    private lateinit var mFragmentList: List<Fragment>

    private lateinit var mTabLayoutMediator: TabLayoutMediator
    private lateinit var mFragmentStateAdapter: FragmentStateAdapter

    companion object {
        private const val EXTRA_MY_SYSTEM = "extra_my_system"
        private const val EXTRA_PAGE_INDEX = "extra_page_index"

        /**
         * 页面启动
         * @param context Context
         */
        fun launch(context: Context, mySystem: MySystem, pageIndex: Int = 0) {
            context.startActivity(Intent(context, SystemArticleListActivity::class.java).apply {
                putExtra(EXTRA_MY_SYSTEM, mySystem)
                putExtra(EXTRA_PAGE_INDEX, pageIndex)
            })
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mySystem = intent.getParcelableExtra(EXTRA_MY_SYSTEM)!!
        mViewModel.title.set(mySystem.name)
        mPageIndex = intent.getIntExtra(EXTRA_PAGE_INDEX, 0)

        mTitleList = mySystem.children.map { it.name }
        mFragmentList =
            (1..mTitleList.size).map { SystemArticleChildFragment.newInstance(mySystem.id) }

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
                offscreenPageLimit = mFragmentList.size
            }

            mTabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.apply {
                    // 处理长按出现toast的问题
                    view.setOnLongClickListener { true }
                    text = mTitleList[position]
                }
            }.apply { attach() }

            viewPager2.currentItem = mPageIndex
        }
    }
}