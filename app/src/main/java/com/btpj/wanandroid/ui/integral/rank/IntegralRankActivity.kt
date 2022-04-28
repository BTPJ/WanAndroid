package com.btpj.wanandroid.ui.integral.rank

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.getEmptyView
import com.btpj.lib_base.ext.initClose
import com.btpj.lib_base.ext.initColors
import com.btpj.lib_base.ext.initTitle
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.BaseActivity
import com.btpj.wanandroid.data.bean.Banner
import com.btpj.wanandroid.data.bean.CoinInfo
import com.btpj.wanandroid.databinding.ActivityIntegralRankBinding
import com.btpj.wanandroid.ui.integral.record.IntegralRecordActivity
import com.btpj.wanandroid.ui.web.WebActivity

/**
 * 积分排行
 *
 * @author LTP 2022/4/12
 */
class IntegralRankActivity :
    BaseActivity<IntegralRankViewModel, ActivityIntegralRankBinding>(R.layout.activity_integral_rank) {

    /** 页数 */
    private var mPageNo: Int = 0
    private val mAdapter by lazy { IntegralAdapter() }

    companion object {

        /**
         * 页面启动
         * @param context Context
         */
        fun launch(context: Context) {
            context.startActivity(Intent(context, IntegralRankActivity::class.java))
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.apply {
            // ToolBar可单独存在，设置setSupportActionBar之后会与菜单键绑定，参考WebActivity中的用法
            includeToolbar.toolbar
                .apply {
                    initTitle("积分排行")
                    initClose { onBackPressed() }
                    inflateMenu(R.menu.menu_integral_rank)
                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.item_integralRule ->
                                // 积分规则
                                WebActivity.launch(
                                    this@IntegralRankActivity,
                                    Banner(
                                        title = "积分规则",
                                        url = "https://www.wanandroid.com/blog/show/2653"
                                    )
                                )

                            R.id.item_integralRecord ->
                                // 积分记录
                                IntegralRecordActivity.launch(this@IntegralRankActivity)
                        }
                        true
                    }
                }

            includeList.apply {
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = mAdapter.apply {
                        loadMoreModule.setOnLoadMoreListener { loadMoreData() }
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
        mViewModel.fetchIntegralRankList()
    }

    /** 下拉加载更多 */
    private fun loadMoreData() {
        // 上拉加载时禁止下拉刷新
        mBinding.includeList.swipeRefreshLayout.isEnabled = false
        mViewModel.fetchIntegralRankList(++mPageNo)
    }

    override fun createObserve() {
        super.createObserve()
        mViewModel.integralPageList.observe(this) {
            handleData(it)
        }
    }

    /**
     * 分页数据处理
     *
     * @param pageResponse
     */
    private fun handleData(pageResponse: PageResponse<CoinInfo>) {
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