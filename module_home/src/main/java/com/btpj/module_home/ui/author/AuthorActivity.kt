package com.btpj.module_home.ui.author

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.base.App
import com.btpj.lib_base.base.BaseVMBActivity
import com.btpj.lib_base.data.bean.Article
import com.btpj.lib_base.data.bean.CollectData
import com.btpj.lib_base.ext.getEmptyView
import com.btpj.lib_base.adapter.ArticleAdapter
import com.btpj.lib_base.export.ModuleHomeApi
import com.btpj.lib_base.ext.initClose
import com.btpj.lib_base.ext.initColors
import com.btpj.module_home.R
import com.btpj.module_home.databinding.HomeActivityAuthorBinding

/**
 * 其他作者的文章列表
 *
 * @author LTP 2022/4/18
 */
@Route(path = ModuleHomeApi.ROUTER_HOME_AUTHOR_ACTIVITY)
class AuthorActivity :
    BaseVMBActivity<AuthorViewModel, HomeActivityAuthorBinding>(R.layout.home_activity_author) {

    /** 作者Id */
    @Autowired(name = ModuleHomeApi.ROUTER_HOME_EXTRA_AUTHOR_ID)
    @JvmField
    var mAuthorId = 0

    /** 页数 */
    private var mPageNo: Int = 0
    private val mAdapter by lazy { ArticleAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)

        mBinding.apply {
            toolbar.initClose { onBackPressed() }

            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = mAdapter.apply {
                    loadMoreModule.setOnLoadMoreListener { loadMoreData() }
                    addChildClickViewIds(com.btpj.lib_base.R.id.iv_collect)
                    setOnItemChildClickListener { _, _, position ->
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

            swipeRefreshLayout.apply {
                initColors()
                setOnRefreshListener { onRefresh() }
            }
        }

        onRefresh()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserve() {
        super.createObserve()
        mViewModel.apply {
            articlePageList.observe(this@AuthorActivity) {
                it?.let { handleArticleData(it) }
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
            mBinding.swipeRefreshLayout.isEnabled = true
        }
        mBinding.swipeRefreshLayout.isRefreshing = false
    }


    /**下拉刷新 */
    private fun onRefresh() {
        mBinding.swipeRefreshLayout.isRefreshing = true
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.loadMoreModule.isEnableLoadMore = false
        mViewModel.apply {
            fetchShareArticlePageList(mAuthorId) {
                mAdapter.setEmptyView(
                    mBinding.recyclerView.getEmptyView("该用户不存在")
                )
                mBinding.swipeRefreshLayout.isRefreshing = false
                true
            }
        }
    }

    /** 下拉加载更多 */
    private fun loadMoreData() {
        // 上拉加载时禁止下拉刷新
        mBinding.swipeRefreshLayout.isEnabled = false
        mViewModel.fetchShareArticlePageList(mAuthorId, ++mPageNo)
    }
}