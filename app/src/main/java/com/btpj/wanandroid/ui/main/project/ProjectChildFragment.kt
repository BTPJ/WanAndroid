package com.btpj.wanandroid.ui.main.project

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.getEmptyView
import com.btpj.lib_base.ext.initColors
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.App
import com.btpj.wanandroid.base.BaseFragment
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.CollectData
import com.btpj.wanandroid.databinding.IncludeSwiperefreshRecyclerviewBinding
import com.btpj.wanandroid.ui.author.AuthorActivity
import com.btpj.wanandroid.ui.main.home.HomeViewModel

/**
 * 项目Tab下的子Fragment
 *
 * @author LTP 2022/3/10
 */
class ProjectChildFragment :
    BaseFragment<ProjectChildViewModel, IncludeSwiperefreshRecyclerviewBinding>(R.layout.include_swiperefresh_recyclerview) {

    /** 列表总数 */
    private var mTotalCount: Int = 0

    /** 页数 */
    private var mPageNo: Int = 0

    /** 当前列表的数量 */
    private var mCurrentCount: Int = 0

    private val mAdapter by lazy { ImageArticleAdapter() }

    companion object {
        private const val IS_NEW = "isNew"
        private const val CATEGORY_ID = "categoryId"

        /**
         * 创建实例
         *
         * @param isNew 是否是最新项目
         * @param categoryId 分类Id
         */
        fun newInstance(isNew: Boolean = false, categoryId: Int = 0) =
            ProjectChildFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_NEW, isNew)
                    putInt(CATEGORY_ID, categoryId)
                }
            }
    }

    override fun initView() {
        mBinding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = mAdapter.apply {
                    loadMoreModule.setOnLoadMoreListener { loadMoreData() }
                    addChildClickViewIds(R.id.tv_author, R.id.iv_collect)
                    setOnItemChildClickListener { _, view, position ->
                        when (view.id) {
                            // 查看作者文章列表
                            R.id.tv_author -> AuthorActivity.launch(
                                requireContext(),
                                mAdapter.getItem(position).userId
                            )
                            R.id.iv_collect ->
                                if (mAdapter.getItem(position).collect) {
                                    mViewModel.unCollectArticle(mAdapter.getItem(position).id) {
                                        // 取消收藏成功后,手动更改避免刷新整个列表
                                        mAdapter.getItem(position).collect = false
                                        mAdapter.notifyItemChanged(position)
                                        App.appViewModel.collectEvent.setValue(
                                            CollectData(
                                                mAdapter.getItem(position).id,
                                                collect = false
                                            )
                                        )
                                    }
                                } else {
                                    mViewModel.collectArticle(mAdapter.getItem(position).id) {
                                        // 收藏成功后,手动更改避免刷新整个列表
                                        mAdapter.getItem(position).collect = true
                                        mAdapter.notifyItemChanged(position)
                                        App.appViewModel.collectEvent.setValue(
                                            CollectData(
                                                mAdapter.getItem(position).id,
                                                collect = true
                                            )
                                        )
                                    }
                                }
                        }
                    }
                }

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

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserve() {
        super.createObserve()
        mViewModel.apply {
            articlePageListLiveData.observe(viewLifecycleOwner) {
                it?.let { handleArticleData(it) }
            }
        }

        App.appViewModel.collectEvent.observe(viewLifecycleOwner) {
            for (position in mAdapter.data.indices) {
                if (mAdapter.data[position].id == it.id) {
                    mAdapter.data[position].collect = it.collect
                    mAdapter.notifyItemChanged(position)
                    break
                }
            }
        }

        // 用户退出时，收藏应全为false，登录时获取collectIds
        App.appViewModel.userEvent.observe(this) {
            if (it != null) {
                it.collectIds.forEach { id ->
                    for (item in mAdapter.data) {
                        if (id.toInt() == item.id) {
                            item.collect = true
                            break
                        }
                    }
                }
            } else {
                for (item in mAdapter.data) {
                    item.collect = false
                }
            }
            mAdapter.notifyDataSetChanged()
        }
    }

    /**
     * 文章分页数据处理
     *
     * @param pageResponse PageResponse
     */
    private fun handleArticleData(pageResponse: PageResponse<Article>) {
        mPageNo = pageResponse.curPage
        mTotalCount = pageResponse.pageCount
        val list = pageResponse.datas
        mAdapter.apply {
            if (mPageNo == 1) {
                if (list.isEmpty()) {
                    setEmptyView(recyclerView.getEmptyView())
                }
                // 如果是加载的第一页数据，用setList()
                setList(list)
            } else {
                // 不是第一页，则用add
                addData(list)
            }
            mCurrentCount = data.size
            loadMoreModule.apply {
                isEnableLoadMore = true
                if (list.size < HomeViewModel.PAGE_SIZE || mCurrentCount == mTotalCount) {
                    // 如果加载到的数据不够一页或都已加载完,显示没有更多数据布局,
                    // 当然后台接口不同分页方式判断方法不同,这个是比较通用的（通常都有TotalCount）
                    loadMoreEnd()
                } else {
                    loadMoreComplete()
                }
            }
            mBinding.swipeRefreshLayout.isEnabled = true
        }
        mBinding.swipeRefreshLayout.isRefreshing = false
    }

    /**
     * 获取文章分页列表
     */
    private fun fetchArticlePageList(pageNo: Int = if (arguments?.getBoolean(IS_NEW) == true) 0 else 1) {
        if (arguments?.getBoolean(IS_NEW) == true) {
            mViewModel.fetchNewProjectPageList(pageNo)
        } else {
            arguments?.getInt(CATEGORY_ID)?.let { mViewModel.fetchProjectPageList(pageNo, it) }
        }
    }

    /**下拉刷新 */
    private fun onRefresh() {
        mBinding.swipeRefreshLayout.isRefreshing = true
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.loadMoreModule.isEnableLoadMore = false
        fetchArticlePageList()
    }

    /** 下拉加载更多 */
    private fun loadMoreData() {
        // 上拉加载时禁止下拉刷新
        mBinding.swipeRefreshLayout.isEnabled = false
        fetchArticlePageList(++mPageNo)
    }
}