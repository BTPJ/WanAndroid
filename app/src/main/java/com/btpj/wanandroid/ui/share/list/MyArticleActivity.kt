package com.btpj.wanandroid.ui.share.list

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.getEmptyView
import com.btpj.lib_base.ext.initColors
import com.btpj.lib_base.ext.showDialog
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.App
import com.btpj.wanandroid.base.BaseActivity
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.databinding.ActivityIntegralRecordBinding
import com.btpj.wanandroid.ui.share.add.AddArticleActivity

/**
 * 我分享的文章列表
 *
 * @author LTP 2022/4/12
 */
class MyArticleActivity :
    BaseActivity<MyArticleViewModel, ActivityIntegralRecordBinding>(R.layout.activity_integral_record) {

    /** 页数 */
    private var mPageNo: Int = 0
    private val mAdapter by lazy { MyArticleAdapter() }

    companion object {

        /**
         * 页面启动
         * @param context Context
         */
        fun launch(context: Context) {
            context.startActivity(Intent(context, MyArticleActivity::class.java))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initView(savedInstanceState: Bundle?) {
        mBinding.apply {
            titleLayout.setTitleText("我分享的文章")
                .setRightView(R.drawable.ic_add) { AddArticleActivity.launch(this@MyArticleActivity) }

            includeList.apply {
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = mAdapter.apply {
                        loadMoreModule.setOnLoadMoreListener { loadMoreData() }
                        addChildClickViewIds(R.id.iv_delete)
                        setOnItemChildClickListener { _, view, position ->
                            when (view.id) {
                                R.id.iv_delete ->
                                    showDialog(
                                        "确定删除该文章吗？",
                                        positiveButtonText = "删除",
                                        positiveAction = {
                                            mViewModel.deleteMyShareArticle(getItem(position).id) {
                                                if (data.size == 1) { // 只剩一个再删除就空了
                                                    setEmptyView(recyclerView.getEmptyView())
                                                }
                                                removeAt(position)
                                            }
                                        }
                                    )
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

        onRefresh()
    }

    /**下拉刷新 */
    private fun onRefresh() {
        mBinding.includeList.swipeRefreshLayout.isRefreshing = true
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.loadMoreModule.isEnableLoadMore = false
        mViewModel.fetchMyShareArticlePageList()
    }

    /** 下拉加载更多 */
    private fun loadMoreData() {
        // 上拉加载时禁止下拉刷新
        mBinding.includeList.swipeRefreshLayout.isEnabled = false
        mViewModel.fetchMyShareArticlePageList(++mPageNo)
    }

    override fun createObserve() {
        super.createObserve()
        mViewModel.articlePageList.observe(this) {
            handleData(it)
        }
        App.appViewModel.shareArticleEvent.observe(this) {
            onRefresh()
        }
    }

    /**
     * 分页数据处理
     *
     * @param pageResponse
     */
    private fun handleData(pageResponse: PageResponse<Article>) {
        mPageNo = pageResponse.curPage
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
            loadMoreModule.apply {
                isEnableLoadMore = true
                if (pageResponse.over) {
                    loadMoreEnd()
                } else {
                    loadMoreComplete()
                }
            }
            mBinding.includeList.swipeRefreshLayout.isEnabled = true
        }
        mBinding.includeList.swipeRefreshLayout.isRefreshing = false
    }
}