package com.btpj.wanandroid.ui.collect.url

import androidx.recyclerview.widget.LinearLayoutManager
import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.lib_base.ext.getEmptyView
import com.btpj.lib_base.ext.initColors
import com.btpj.wanandroid.R
import com.btpj.wanandroid.databinding.IncludeSwiperefreshRecyclerviewBinding

/**
 * 收藏的文章
 *
 * @author LTP 2022/3/10
 */
class CollectUrlFragment :
    BaseVMBFragment<CollectUrlViewModel, IncludeSwiperefreshRecyclerviewBinding>(R.layout.include_swiperefresh_recyclerview) {

    private val mAdapter by lazy { CollectUrlAdapter() }

    companion object {

        /** 创建实例 */
        fun newInstance() = CollectUrlFragment()
    }

    override fun initView() {
        mBinding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = mAdapter

                swipeRefreshLayout.apply {
                    initColors()
                    setOnRefreshListener { onRefresh() }
                }
            }
        }

        onRefresh()
    }

    override fun createObserve() {
        super.createObserve()
        mViewModel.collectUrlList.observe(viewLifecycleOwner) {
            mBinding.swipeRefreshLayout.isRefreshing = false
            mAdapter.apply {
                if (it.isEmpty()) {
                    setEmptyView(mBinding.recyclerView.getEmptyView())
                } else {
                    setList(it)
                }
            }
        }
    }

    /**下拉刷新 */
    private fun onRefresh() {
        mBinding.swipeRefreshLayout.isRefreshing = true
        mViewModel.fetchCollectUrlList()
    }
}