package com.btpj.wanandroid.ui.collect.article

import androidx.recyclerview.widget.LinearLayoutManager
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.getEmptyView
import com.btpj.lib_base.ext.initColors
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.App
import com.btpj.wanandroid.base.BaseFragment
import com.btpj.wanandroid.data.bean.CollectArticle
import com.btpj.wanandroid.databinding.IncludeSwiperefreshRecyclerviewBinding
import java.util.*

/**
 * 收藏的文章
 *
 * @author LTP 2022/3/10
 */
class CollectArticleFragment :
    BaseFragment<CollectArticleViewModel, IncludeSwiperefreshRecyclerviewBinding>(R.layout.include_swiperefresh_recyclerview) {

    /** 页数 */
    private var mPageNo: Int = 0
    private val mAdapter by lazy { CollectArticleAdapter() }

    companion object {

        /** 创建实例 */
        fun newInstance() = CollectArticleFragment()
    }

    override fun initView() {
        mBinding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = mAdapter.apply {
                    loadMoreModule.setOnLoadMoreListener { loadMoreData() }
                    addChildClickViewIds(R.id.iv_collect)
                    setOnItemChildClickListener { _, _, position ->
                        mViewModel.unCollectArticle(mAdapter.getItem(position).originId) {
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
        mViewModel.apply {
            collectArticlePageList.observe(viewLifecycleOwner) {
                it?.let { handleArticleData(it) }
            }
        }

        App.appViewModel.collectEvent.observe(viewLifecycleOwner) {
            if (!it.collect) {
                for (position in mAdapter.data.indices) {
                    if (mAdapter.data[position].id == it.id) {
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
                    if (mAdapter.data[position].id == it.id) {
                        // 取消收藏再次收藏会回到第一个,收藏时间也会变为 “刚刚”
                        // -----不想重新请求刷新的话，这种处理会有问题--start-------
                        // mAdapter.data[position].niceDate = "刚刚"
                        // mAdapter.notifyItemChanged(position)
                        // mAdapter.notifyItemMoved(position, 0)
                        // --------------------end-------------------------------

                        // 采用直接操作List的方式,分页请求时重新刷新体验不太好
                        val oldList = ArrayList(mAdapter.data)
                        val tempList = mAdapter.data.subList(0, position)
                        tempList.add(0, oldList[position].apply { niceDate = "刚刚" })
                        val newList = ArrayList<CollectArticle>()
                        newList.addAll(tempList)
                        newList.addAll(oldList.subList(position + 1, oldList.size))
                        mAdapter.setList(newList)
                        break
                    }
                }
            }
        }
    }

    /**
     * 文章分页数据处理
     *
     * @param pageResponse PageResponse
     */
    private fun handleArticleData(pageResponse: PageResponse<CollectArticle>) {
        mPageNo = pageResponse.curPage
        val list = pageResponse.datas
        mAdapter.apply {
            if (mPageNo == 1) {
                if (list.isEmpty()) {
                    setEmptyView(recyclerView.getEmptyView())
                }
                // 如果是加载的第一页数据，用 setData()
                setList(list)
            } else {
                // 不是第一页，则用add
                addData(list)
            }
            loadMoreModule.apply {
                isEnableLoadMore = true
                if (pageResponse.over) {
                    loadMoreEnd()
                } else {
                    loadMoreComplete()
                }
            }
            mBinding.swipeRefreshLayout.isEnabled = true
        }
        mBinding.swipeRefreshLayout.isRefreshing = false
    }

    /**下拉刷新 */
    private fun onRefresh() {
        mBinding.swipeRefreshLayout.isRefreshing = true
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.loadMoreModule.isEnableLoadMore = false
        mViewModel.fetchCollectArticlePageList()
    }

    /** 下拉加载更多 */
    private fun loadMoreData() {
        // 上拉加载时禁止下拉刷新
        mBinding.swipeRefreshLayout.isEnabled = false
        mViewModel.fetchCollectArticlePageList(++mPageNo)
    }
}