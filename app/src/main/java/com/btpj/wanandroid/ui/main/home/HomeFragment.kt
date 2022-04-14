package com.btpj.wanandroid.ui.main.home

import android.annotation.SuppressLint
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.btpj.wanandroid.base.BaseFragment
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.getEmptyView
import com.btpj.lib_base.ext.initColors
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.Banner
import com.btpj.wanandroid.databinding.FragmentHomeBinding
import com.btpj.wanandroid.databinding.HeaderBannerBinding
import com.btpj.wanandroid.ui.main.home.HomeViewModel.Companion.PAGE_SIZE
import com.youth.banner.indicator.CircleIndicator

/**
 * 首页Tab
 *
 * @author LTP 2022/3/10
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {

    /** 列表总数 */
    private var mTotalCount: Int = 0

    /** 页数 */
    private var mPageNo: Int = 0

    /** 当前列表的数量 */
    private var mCurrentCount: Int = 0

    private val mBannerList = ArrayList<Banner>()
    private val mBannerAdapter = MyBannerAdapter(mBannerList)

    private val mAdapter by lazy { ArticleAdapter() }

    companion object {
        fun newInstance() = HomeFragment()
    }

    @SuppressLint("InflateParams")
    override fun initView() {
        val headerBannerBinding = DataBindingUtil.inflate<HeaderBannerBinding>(
            layoutInflater,
            R.layout.header_banner,
            null,
            false
        ).apply {
            banner.apply {
                setAdapter(mBannerAdapter)
                setIndicator(CircleIndicator(context))
            }
        }

        mBinding.includeList.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = mAdapter.apply {
                    loadMoreModule.setOnLoadMoreListener { loadMoreData() }
                    setHeaderView(headerBannerBinding.root)
                    addChildClickViewIds(R.id.iv_collect)
                    setOnItemChildClickListener { _, _, position ->
                        if (mAdapter.getItem(position).collect) {
                            mViewModel.unCollectArticle(mAdapter.getItem(position).id) {
                                // 取消收藏成功后,手动更改避免刷新整个列表
                                mAdapter.getItem(position).collect = false
                                // 注意:这里position需要+1,因为0位置属于轮播图HeaderView
                                mAdapter.notifyItemChanged(position + 1)
                            }
                        } else {
                            mViewModel.collectArticle(mAdapter.getItem(position).id) {
                                // 收藏成功后,手动更改避免刷新整个列表
                                mAdapter.getItem(position).collect = true
                                mAdapter.notifyItemChanged(position + 1)
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