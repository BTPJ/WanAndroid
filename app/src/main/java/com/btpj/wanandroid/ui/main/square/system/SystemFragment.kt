package com.btpj.wanandroid.ui.main.square.system

import androidx.recyclerview.widget.LinearLayoutManager
import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.lib_base.ext.getEmptyView
import com.btpj.lib_base.ext.initColors
import com.btpj.wanandroid.R
import com.btpj.wanandroid.databinding.IncludeSwiperefreshRecyclerviewBinding

/**
 * 广场Tab下的体系
 *
 * @author LTP 2022/4/7
 */
class SystemFragment :
    BaseVMBFragment<SystemViewModel, IncludeSwiperefreshRecyclerviewBinding>(R.layout.include_swiperefresh_recyclerview) {

    private val mAdapter by lazy { SystemAdapter() }

    companion object {
        fun newInstance() = SystemFragment()
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
        mViewModel.systemListLiveData.observe(viewLifecycleOwner) {
            mBinding.swipeRefreshLayout.isRefreshing = false
            mAdapter.apply {
                if (it.isEmpty()) {
                    setEmptyView(getEmptyView(mBinding.recyclerView))
                } else {
                    setList(it)
                }
            }
        }
    }

    /**下拉刷新 */
    private fun onRefresh() {
        mBinding.swipeRefreshLayout.isRefreshing = true
        mViewModel.fetchSystemList()
    }
}