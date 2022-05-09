package com.btpj.module_home.ui.home

import android.annotation.SuppressLint
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.btpj.lib_base.adapter.ArticleAdapter
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.getEmptyView
import com.btpj.lib_base.base.App
import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.lib_base.data.bean.Article
import com.btpj.lib_base.data.bean.Banner
import com.btpj.lib_base.data.bean.CollectData
import com.btpj.lib_base.data.local.Constants
import com.btpj.lib_base.ext.initColors
import com.btpj.module_home.R
import com.btpj.module_home.databinding.HomeFragmentHomeBinding
import com.btpj.module_home.databinding.HomeHeaderBannerBinding
import com.btpj.module_home.ui.author.AuthorActivity
import com.btpj.module_home.ui.home.HomeViewModel.Companion.PAGE_SIZE
import com.btpj.module_home.ui.search.SearchActivity
import com.youth.banner.indicator.CircleIndicator

/**
 * 首页Tab
 *
 * @author LTP 2022/3/10
 */
@Route(path = Constants.ROUTER_HOME_HOME_FRAGMENT)
class HomeFragment :
    BaseVMBFragment<HomeViewModel, HomeFragmentHomeBinding>(R.layout.home_fragment_home) {

    /** 列表总数 */
    private var mTotalCount: Int = 0

    /** 页数 */
    private var mPageNo: Int = 0

    /** 当前列表的数量 */
    private var mCurrentCount: Int = 0

    private val mBannerList = ArrayList<Banner>()
    private val mBannerAdapter = MyBannerAdapter(mBannerList)

    private val mAdapter by lazy { ArticleAdapter() }

    @SuppressLint("InflateParams")
    override fun initView() {
        val headerBannerBinding = DataBindingUtil.inflate<HomeHeaderBannerBinding>(
            layoutInflater,
            R.layout.home_header_banner,
            null,
            false
        ).apply {
            banner.apply {
                setAdapter(mBannerAdapter)
                indicator = CircleIndicator(context)
                addBannerLifecycleObserver(viewLifecycleOwner)
            }
        }

        mBinding.apply {
            titleLayout.setRightView(com.btpj.lib_base.R.drawable.ic_search) {
                SearchActivity.launch(requireContext())
            }

            includeList.apply {
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = mAdapter.apply {
                        loadMoreModule.setOnLoadMoreListener { loadMoreData() }
                        addHeaderView(headerBannerBinding.root)
                        addChildClickViewIds(
                            com.btpj.lib_base.R.id.tv_author,
                            com.btpj.lib_base.R.id.iv_collect
                        )
                        setOnItemChildClickListener { _, view, position ->
                            when (view.id) {
                                // 查看作者文章列表
                                com.btpj.lib_base.R.id.tv_author ->
                                    AuthorActivity.launch(
                                        requireContext(),
                                        mAdapter.getItem(position).userId
                                    )
                                // 收藏与取消收藏
                                com.btpj.lib_base.R.id.iv_collect ->
                                    if (mAdapter.getItem(position).collect) {
                                        mViewModel.unCollectArticle(mAdapter.getItem(position).id) {
                                            // 取消收藏成功后,手动更改避免刷新整个列表
                                            mAdapter.getItem(position).collect = false
                                            // 注意:这里position需要+1,因为0位置属于轮播图HeaderView
                                            mAdapter.notifyItemChanged(position + 1)
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
                                            mAdapter.notifyItemChanged(position + 1)
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
            bannerListLiveData.observe(viewLifecycleOwner) {
                it?.let {
                    mBannerList.apply {
                        clear()
                        addAll(it)
                    }
                }
                mBannerAdapter.notifyDataSetChanged()
            }

            articlePageListLiveData.observe(viewLifecycleOwner) {
                it?.let { handleArticleData(it) }
            }
        }

        // 全局收藏监听
        App.appViewModel.collectEvent.observe(viewLifecycleOwner) {
            for (position in mAdapter.data.indices) {
                if (mAdapter.data[position].id == it.id) {
                    mAdapter.data[position].collect = it.collect
                    mAdapter.notifyItemChanged(position + 1)
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
     * @param pageResponse
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
                // 如果是加载的第一页数据，用 setData()
                setList(list)
            } else {
                // 不是第一页，则用add
                addData(list)
            }
            mCurrentCount = data.size
            loadMoreModule.apply {
                isEnableLoadMore = true
                if (list.size < PAGE_SIZE || mCurrentCount == mTotalCount) {
                    // 如果加载到的数据不够一页或都已加载完,显示没有更多数据布局,
                    // 当然后台接口不同分页方式判断方法不同,这个是比较通用的（通常都有TotalCount）
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
        mViewModel.apply {
            fetchBanners()
            fetchArticlePageList()
        }
    }

    /** 下拉加载更多 */
    private fun loadMoreData() {
        // 上拉加载时禁止下拉刷新
        mBinding.includeList.swipeRefreshLayout.isEnabled = false
        mViewModel.fetchArticlePageList(++mPageNo)
    }

    override fun requestError(msg: String?) {
        super.requestError(msg)
        mBinding.includeList.swipeRefreshLayout.isRefreshing = false
    }
}