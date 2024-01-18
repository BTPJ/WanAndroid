package com.btpj.module_square.ui.square.system

import androidx.recyclerview.widget.LinearLayoutManager
import com.btpj.lib_base.R
import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.lib_base.databinding.IncludeSwiperefreshRecyclerviewBinding
import com.btpj.lib_base.ext.getEmptyView
import com.btpj.lib_base.ext.initColors
import com.btpj.module_square.ui.square.system.details.SystemArticleListActivity

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
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        onRefresh()
    }

    override fun createObserve() {
        super.createObserve()
        mViewModel.systemListLiveData.observe(viewLifecycleOwner) {
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
        mViewModel.fetchSystemList()
    }

    override fun requestError(msg: String?) {
        super.requestError(msg)
        mBinding.swipeRefreshLayout.isRefreshing = false
    }
}