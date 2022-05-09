package com.btpj.module_main.data.local

import com.tencent.mmkv.MMKV

/**
 * 项目中用到的一些缓存
 *
 * @author LTP 2022/4/8
 */
object CacheManager {

    private const val KEY_FIRST_USE = "first_use"

    private val mmkv by lazy { MMKV.defaultMMKV() }

    /** 存储是否首次使用APP */
    fun saveFirstUse(isFirstUse: Boolean): Boolean {
        return mmkv.encode(KEY_FIRST_USE, isFirstUse)
    }

    /** 是否首次使用APP */
    fun isFirstUse(): Boolean {
        return mmkv.decodeBool(KEY_FIRST_USE, true)
    }
}