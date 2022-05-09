package com.btpj.module_home.data

import com.btpj.lib_base.data.bean.*
import com.btpj.lib_base.http.BaseRepository
import com.btpj.lib_base.http.RetrofitManager
import com.btpj.module_home.data.http.Api
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 数据仓库
 *
 * @author LTP  2022/3/23
 */
object DataRepository : BaseRepository(), Api {

    private val service by lazy { RetrofitManager.getService(Api::class.java) }

    override suspend fun getBanner(): ApiResponse<List<Banner>> {
        return apiCall { service.getBanner() }
    }

    override suspend fun getArticleTopList(): ApiResponse<List<Article>> {
        return apiCall { service.getArticleTopList() }
    }

    override suspend fun getArticlePageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { service.getArticlePageList(pageNo, pageSize) }
    }

    override suspend fun collectArticle(id: Int): ApiResponse<Any?> {
        return apiCall { service.collectArticle(id) }
    }

    override suspend fun unCollectArticle(id: Int): ApiResponse<Any?> {
        return apiCall { service.unCollectArticle(id) }
    }

    override suspend fun getOtherAuthorArticlePageList(
        id: Int,
        page: Int
    ): ApiResponse<OtherAuthor> {
        return apiCall { service.getOtherAuthorArticlePageList(id, page) }
    }

    override suspend fun getHotSearchList(): ApiResponse<List<HotSearch>> {
        return apiCall { service.getHotSearchList() }
    }

    override suspend fun getSearchDataByKey(
        pageNo: Int,
        searchKey: String
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { service.getSearchDataByKey(pageNo, searchKey) }
    }
}