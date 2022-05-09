package com.btpj.module_square.ui.square.navigation

import androidx.recyclerview.widget.LinearLayoutManager
import com.btpj.lib_base.ext.getEmptyView
import com.btpj.lib_base.databinding.IncludeSwiperefreshRecyclerviewBinding
import com.btpj.lib_base.R
import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.lib_base.ext.initColors


/**
 * 广场Tab下的体系
 *
 * @author LTP 2022/4/7
 */
class NavigationFragment :
    BaseVMBFragment<NavigationViewModel, IncludeSwiperefreshRecyclerviewBinding>(R.layout.include_swiperefresh_recyclerview) {

    private val mAdapter by lazy { NavigationAdapter() }

    companion object {
        fun newInstance() = NavigationFragment()
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
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        onRefresh()
    }

    override fun createObserve() {
        super.createObserve()
        mViewModel.navigationListLiveData.observe(viewLifecycleOwner) {
            mBinding.swipeRefreshLayout.isRefreshing = false
            mAdapter.apply {
                if (it.isEmpty()) {
                    setEmptyView(recyclerView.getEmptyView())
                } else {
                    setList(it)
                }
            }
        }
    }

    /**下拉刷新 */
    private fun onRefresh() {
        mBinding.swipeRefreshLayout.isRefreshing = true
        mViewModel.fetchNavigationList()
    }

    override fun requestError(msg: String?) {
        super.requestError(msg)
        mBinding.swipeRefreshLayout.isRefreshing = false
    }
}