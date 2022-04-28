package com.btpj.wanandroid.ui.collect.url

import androidx.recyclerview.widget.LinearLayoutManager
import com.btpj.lib_base.ext.getEmptyView
import com.btpj.lib_base.ext.initColors
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.App
import com.btpj.wanandroid.base.BaseFragment
import com.btpj.wanandroid.databinding.IncludeSwiperefreshRecyclerviewBinding

/**
 * 收藏的文章
 *
 * @author LTP 2022/3/10
 */
class CollectUrlFragment :
    BaseFragment<CollectUrlViewModel, IncludeSwiperefreshRecyclerviewBinding>(R.layout.include_swiperefresh_recyclerview) {

    private val mAdapter by lazy { CollectUrlAdapter() }

    companion object {

        /** 创建实例 */
        fun newInstance() = CollectUrlFragment()
    }

    override fun initView() {
        mBinding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = mAdapter.apply {
                    addChildClickViewIds(R.id.iv_collect)
                    setOnItemChildClickListener { _, _, position ->
                        mViewModel.unCollectUrl(mAdapter.getItem(position).id) {
                            // 取消收藏成功后,直接删除
                            mAdapter.removeAt(position)
                        }
                    }
                }

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
                }
                setList(it)
            }
        }

        App.appViewModel.collectEvent.observe(viewLifecycleOwner) {
            // 连续收藏取消收藏，id会变这里直接以link来匹配
            if (!it.collect) {
                for (position in mAdapter.data.indices) {
                    if (mAdapter.data[position].link == it.link) {
                        mAdapter.removeAt(position)
                        if (mAdapter.data.isEmpty()) {
                            mAdapter.setEmptyView(mBinding.recyclerView.getEmptyView())
                            mAdapter.setList(null)
                        }
                        break
                    }
                }
            } else {
                for (position in mAdapter.data.indices) {
                    if (mAdapter.data[position].link == it.link) {
                        // 重新收藏的collectUrl的id会变，重新赋值即可
                        mAdapter.data[position].id = it.id
                        mAdapter.notifyItemChanged(position)
                        break
                    }
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