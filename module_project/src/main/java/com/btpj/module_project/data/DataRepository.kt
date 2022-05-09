package com.btpj.module_project.data

import com.btpj.lib_base.data.bean.*
import com.btpj.lib_base.http.BaseRepository
import com.btpj.lib_base.http.RetrofitManager
import com.btpj.module_project.data.http.Api
import retrofit2.http.Path
import retrofit2.http.Query

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

    override suspend fun getProjectTitleList(): ApiResponse<List<ProjectTitle>> {
        return apiCall { service.getProjectTitleList() }
    }

    override suspend fun getNewProjectPageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { service.getNewProjectPageList(pageNo, pageSize) }
    }

    override suspend fun getProjectPageList(
        @Path(value = "pageNo") pageNo: Int,
        @Query(value = "page_size") pageSize: Int,
        @Query(value = "cid") categoryId: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { service.getProjectPageList(pageNo, pageSize, categoryId) }
    }
}