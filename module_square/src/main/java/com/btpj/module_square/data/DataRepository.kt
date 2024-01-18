package com.btpj.module_square.data

import com.btpj.lib_base.data.bean.*
import com.btpj.lib_base.http.BaseRepository
import com.btpj.lib_base.http.RetrofitManager
import com.btpj.module_square.data.http.Api

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

    override suspend fun getSquarePageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { service.getSquarePageList(pageNo, pageSize) }
    }

    override suspend fun getAskPageList(
        pageNo: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { service.getAskPageList(pageNo) }
    }

    override suspend fun getTreeList(): ApiResponse<List<MySystem>> {
        return apiCall { service.getTreeList() }
    }

    override suspend fun getNavigationList(): ApiResponse<List<Navigation>> {
        return apiCall { service.getNavigationList() }
    }

    override suspend fun getSystemArticlePageList(
        pageNo: Int,
        pageSize: Int,
        categoryId: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { service.getSystemArticlePageList(pageNo, pageSize, categoryId) }
    }
}