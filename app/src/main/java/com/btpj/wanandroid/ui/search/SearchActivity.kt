package com.btpj.wanandroid.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.btpj.lib_base.utils.ToastUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.BaseActivity
import com.btpj.wanandroid.data.local.CacheManager
import com.btpj.wanandroid.databinding.ActivitySearchBinding
import com.btpj.wanandroid.ui.search.result.SearchResultActivity
import com.google.android.flexbox.FlexboxLayoutManager

/**
 * 搜索
 *
 * @author LTP 2022/4/19
 */
class SearchActivity :
    BaseActivity<SearchViewModel, ActivitySearchBinding>(R.layout.activity_search) {

    /** 搜索历史 */
    private val mHistoryAdapter by lazy { SearchHistoryAdapter() }

    /** 热门搜索 */
    private val mHotSearchAdapter by lazy { HotSearchAdapter() }

    companion object {

        /**
         * 页面启动
         * @param context Context
         */
        fun launch(context: Context) {
            context.startActivity(Intent(context, SearchActivity::class.java))
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.apply {
            // toolbar + SearchView挺麻烦不想用
            titleLayout.setRightView(R.drawable.ic_search) {
                handleSearch()
            }

            rvHotSearch.apply {
                isNestedScrollingEnabled = false // 在NestedScrollView中不允许自己滑动
                layoutManager = FlexboxLayoutManager(this@SearchActivity)
                adapter = mHotSearchAdapter.apply {
                    setOnItemClickListener { _, _, position ->
                        SearchResultActivity.launch(this@SearchActivity, this.data[position].name)
                    }
                }
            }

            rvSearchHistory.apply {
                isNestedScrollingEnabled = false // 在NestedScrollView中不允许自己滑动
                layoutManager = LinearLayoutManager(this@SearchActivity)
                adapter = mHistoryAdapter.apply {
                    setOnItemClickListener { _, _, position ->
                        SearchResultActivity.launch(this@SearchActivity, this.data[position])
                    }
                    addChildClickViewIds(R.id.iv_delete)
                    setOnItemChildClickListener { _, _, position ->
                        mViewModel.searchHistoryData.value?.removeAt(position)
                        removeAt(position)
                    }
                }
            }

            // 监听软键盘的搜索键
            etSearch.setOnEditorActionListener { _, _, _ ->
                handleSearch()
                return@setOnEditorActionListener true
            }
        }
    }

    /** 搜索事件处理 */
    private fun handleSearch() {
        mViewModel.searchKeyStr.value.let {
            if (it?.trim()?.isNotEmpty() == true) {
                updateKey(it)
                SearchResultActivity.launch(this@SearchActivity, it)
            } else {
                ToastUtil.showShort(this@SearchActivity, "请输入搜索关键词")
            }
        }
    }

    override fun createObserve() {
        super.createObserve()
        mViewModel.apply {
            hotSearchList.observe(this@SearchActivity) {
                mHotSearchAdapter.setList(it)
            }

            searchHistoryData.observe(this@SearchActivity) {
                mHistoryAdapter.setList(it)
                CacheManager.saveSearchHistoryData(it)
            }
        }
    }

    /**
     * 更新搜索关键词
     * @param keyStr 要更新的搜索关键词
     */
    private fun updateKey(keyStr: String) {
        mViewModel.searchHistoryData.value?.apply {
            if (contains(keyStr)) {
                //当搜索历史中包含该数据时 删除
                remove(keyStr)
            } else if (size >= 10) {
                //如果集合的size 有10个以上了，删除最后一个
                removeLast()
            }
            //添加新数据到第一条
            addFirst(keyStr)
            mViewModel.searchHistoryData.value = this
        }
    }
}