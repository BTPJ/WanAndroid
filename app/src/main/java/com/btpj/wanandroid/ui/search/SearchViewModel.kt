package com.btpj.wanandroid.ui.search

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.HotSearch
import com.btpj.wanandroid.data.local.CacheManager

/**
 * @author LTP  2022/4/19
 */
class SearchViewModel : BaseViewModel() {

    /** 搜索关键词 */
    val searchKeyStr = MutableLiveData<String>()

    /** 搜索历史数据 */
    val searchHistoryData = MutableLiveData<ArrayDeque<String>>()

    /** 热门搜索 */
    val hotSearchList = MutableLiveData<List<HotSearch>>()

    override fun start() {
        fetchHotSearchList()
        fetchSearchHistoryData()
    }

    /** 获取历史搜索记录 */
    private fun fetchSearchHistoryData() {
        launch({
            searchHistoryData.value = CacheManager.getSearchHistoryData()
        })
    }

    /** 获取热门搜索 */
    private fun fetchHotSearchList() {
        launch({
            handleRequest(DataRepository.getHotSearchList(), {
                hotSearchList.value = it.data
            })
        })
    }

    /** 清空历史 */
    fun clearHistory() {
        searchHistoryData.value = ArrayDeque()
    }
}