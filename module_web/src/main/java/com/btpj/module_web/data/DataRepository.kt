package com.btpj.module_web.data

import com.btpj.lib_base.data.bean.ApiResponse
import com.btpj.lib_base.http.BaseRepository
import com.btpj.lib_base.http.RetrofitManager
import com.btpj.lib_base.data.bean.CollectUrl
import com.btpj.module_web.data.http.Api

/**
 * 数据仓库
 *
 * @author LTP  2022/3/23
 */
object DataRepository : BaseRepository(), Api {

    private val service by lazy { RetrofitManager.getService(Api::class.java) }

    override suspend fun collectArticle(id: Int): ApiResponse<Any?> {
        return apiCall { service.collectArticle(id) }
    }

    override suspend fun unCollectArticle(id: Int): ApiResponse<Any?> {
        return apiCall { service.unCollectArticle(id) }
    }

    override suspend fun collectUrl(name: String, link: String): ApiResponse<CollectUrl?> {
        return apiCall { service.collectUrl(name, link) }
    }

    override suspend fun unCollectUrl(id: Int): ApiResponse<Any?> {
        return apiCall { service.unCollectUrl(id) }
    }

}