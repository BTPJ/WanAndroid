package com.btpj.wanandroid.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.btpj.wanandroid.base.BaseViewModel
import com.btpj.wanandroid.data.DataRepository
import com.btpj.wanandroid.data.bean.HotSearch
import com.btpj.wanandroid.data.local.CacheManager

/**
 * @author LTP  2022/4/19
 */
class SearchViewModel : BaseViewModel<Unit>() {

    /** 搜索历史数据 */
    private val _searchHistoryData = MutableLiveData<ArrayDeque<String>>()
    val searchHistoryData: LiveData<ArrayDeque<String>> = _searchHistoryData

    /** 热门搜索 */
    private val _hotSearchList = MutableLiveData<List<HotSearch>?>()
    val hotSearchList: LiveData<List<HotSearch>?> = _hotSearchList

    /** 获取历史搜索记录 */
    fun fetchSearchHistoryData() {
        launch({
            _searchHistoryData.value = CacheManager.getSearchHistoryData()
        })
    }

    /** 获取热门搜索 */
    fun fetchHotSearchList() {
        launch({
            handleRequest(DataRepository.getHotSearchList()) {
                _hotSearchList.value = it.data
            }
        })
    }

    /** 清空历史 */
    fun clearHistory() {
        _searchHistoryData.value = ArrayDeque()
    }

    /** 处理搜索框点击 */
    fun handleSearchClick(searchText: String) {
        _searchHistoryData.value?.apply {
            if (contains(searchText)) {
                //当搜索历史中包含该数据时 删除
                remove(searchText)
            } else if (size >= 10) {
                //如果集合的size 有10个以上了，删除最后一个
                removeLast()
            }
            //添加新数据到第一条
            addFirst(searchText)
            _searchHistoryData.value = this
        }
    }

    /** 处理搜索历史删除 */
    fun handleDeleteHistoryItem(item: String) {
        _searchHistoryData.value?.remove(item)
        _searchHistoryData.value = _searchHistoryData.value
    }

    /** 处理搜索历史点击（主要是将点击的移到第一个） */
    fun handleHistoryItemClick(item: String) {
        _searchHistoryData.value?.remove(item)
        _searchHistoryData.value?.addFirst(item)
    }
}