package com.btpj.wanandroid.ui.search.result

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.getEmptyView
import com.btpj.lib_base.ext.initColors
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.App
import com.btpj.wanandroid.base.BaseActivity
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.CollectData
import com.btpj.wanandroid.databinding.ActivitySearchResultBinding
import com.btpj.wanandroid.ui.author.AuthorActivity
import com.btpj.wanandroid.ui.main.home.ArticleAdapter

/**
 * 搜索结果列表
 *
 * @author LTP 2022/4/19
 */
class SearchResultActivity :
    BaseActivity<SearchResultViewModel, ActivitySearchResultBinding>(R.layout.activity_search_result) {

    private lateinit var mSearchKeyStr: String

    private var mPageNo = 0
    private val mAdapter by lazy { ArticleAdapter() }

    companion object {

        private const val EXTRA_SEARCH_KEY = "search_key"

        /**
         * 页面启动
         * @param context Context
         * @param searchKeyStr 搜索关键词
         */
        fun launch(context: Context, searchKeyStr: String) {
            context.startActivity(Intent(context, SearchResultActivity::class.java).apply {
                putExtra(EXTRA_SEARCH_KEY, searchKeyStr)
            })
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mSearchKeyStr = intent.getStringExtra(EXTRA_SEARCH_KEY) ?: ""

        mBinding.apply {
            titleLayout.setTitleText(mSearchKeyStr)

            includeList.apply {
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = mAdapter.apply {
                        loadMoreModule.setOnLoadMoreListener { loadMoreData() }
                        addChildClickViewIds(R.id.tv_author, R.id.iv_collect)
                        setOnItemChildClickListener { _, view, position ->
                            when (view.id) {
                                // 查看作者文章列表
                                R.id.tv_author ->
                                    AuthorActivity.launch(
                                        this@SearchResultActivity,
                                        mAdapter.getItem(position).userId
                                    )
                                // 收藏与取消收藏
                                R.id.iv_collect ->
                                    if (mAdapter.getItem(position).collect) {
                                        mViewModel.unCollectArticle(mAdapter.getItem(position).id) {
                                            // 取消收藏成功后,手动更改避免刷新整个列表
                                            mAdapter.getItem(position).collect = false
                                            mAdapter.notifyItemChanged(position)
                                            App.appViewModel.collectEvent.setValue(
                                                CollectData(
                                                    mAdapter.getItem(
                                                        position
                                                    ).id, collect = false
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
                                                    mAdapter.getItem(
                                                        position
                                                    ).id, collect = true
                                                )
                                            )
                                        }
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

        onRefresh()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserve() {
        super.createObserve()
        mViewModel.apply {
            articlePageList.observe(this@SearchResultActivity) {
                it?.let { handleArticleData(it) }
            }
        }

        // 全局收藏监听
        App.appViewModel.collectEvent.observe(this) {
            for (position in mAdapter.data.indices) {
                if (mAdapter.data[position].id == it.id) {
                    mAdapter.data[position].collect = it.collect
                    mAdapter.notifyItemChanged(position)
                    break
                }
            }
        }
    }

    /**
     * 文章分页数据处理
     *
     * @param pageResponse
     */
    private fun handleArticleData(pageResponse: PageResponse<Article>) {
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
            mBinding.includeList.swipeRefreshLayout.isEnabled = true
        }
        mBinding.includeList.swipeRefreshLayout.isRefreshing = false
    }


    /**下拉刷新 */
    private fun onRefresh() {
        mBinding.includeList.swipeRefreshLayout.isRefreshing = true
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.loadMoreModule.isEnableLoadMore = false
        mViewModel.fetchSearchResultPageList(mSearchKeyStr)
    }

    /** 下拉加载更多 */
    private fun loadMoreData() {
        // 上拉加载时禁止下拉刷新
        mBinding.includeList.swipeRefreshLayout.isEnabled = false
        mViewModel.fetchSearchResultPageList(mSearchKeyStr, ++mPageNo)
    }
}