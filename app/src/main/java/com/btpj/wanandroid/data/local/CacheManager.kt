package com.btpj.wanandroid.data.local

import android.text.TextUtils
import com.btpj.lib_base.ext.toJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV

/**
 * 项目中用到的一些缓存
 *
 * @author LTP 2022/4/8
 */
object CacheManager {

    private const val KEY_SEARCH_HISTORY = "search_history"
    private const val KEY_FIRST_USE = "first_use"

    private val mmkv by lazy { MMKV.defaultMMKV() }

    /** 获取搜索历史缓存数据 */
    fun getSearchHistoryData(): ArrayDeque<String>? {
        val searchCacheStr = mmkv.decodeString(KEY_SEARCH_HISTORY)
        if (!TextUtils.isEmpty(searchCacheStr)) {
            return Gson().fromJson(searchCacheStr, object : TypeToken<ArrayDeque<String>>() {}.type)
        }
        return ArrayDeque()
    }

    /** 存储搜索历史数据 */
    fun saveSearchHistoryData(searchHistoryDeque: ArrayDeque<String>) {
        mmkv.encode(KEY_SEARCH_HISTORY, searchHistoryDeque.toJson())
    }

    /** 存储是否首次使用APP */
    fun saveFirstUse(isFirstUse: Boolean): Boolean {
        return mmkv.encode(KEY_FIRST_USE, isFirstUse)
    }

    /** 是否首次使用APP */
    fun isFirstUse(): Boolean {
        return mmkv.decodeBool(KEY_FIRST_USE, true)
    }
}