package com.btpj.lib_base.http

import com.btpj.lib_base.data.bean.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository数据仓库基类，主要用于协程的调用
 *
 * @author LTP  2022/3/23
 */
open class BaseRepository {

    suspend fun <T> apiCall(api: suspend () -> ApiResponse<T>): ApiResponse<T> {
        return withContext(Dispatchers.IO) { api.invoke() }
    }
}