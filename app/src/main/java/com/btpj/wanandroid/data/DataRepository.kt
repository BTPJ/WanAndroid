package com.btpj.wanandroid.data

import com.btpj.lib_base.bean.ApiResponse
import com.btpj.lib_base.bean.PageResponse
import com.btpj.lib_base.http.BaseRepository
import com.btpj.wanandroid.data.http.Api
import com.btpj.lib_base.http.RetrofitManager
import com.btpj.wanandroid.data.bean.*
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 数据仓库
 *
 * @author LTP  2022/3/23
 */
object DataRepository : BaseRepository(), Api {

    private val service by lazy { RetrofitManager.getService(Api::class.java) }

    override suspend fun login(username: String, pwd: String): ApiResponse<User> {
        return apiCall { service.login(username, pwd) }
    }

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

    override suspend fun getTreeList(): ApiResponse<List<System>> {
        return apiCall { service.getTreeList() }
    }

    override suspend fun getNavigationList(): ApiResponse<List<Navigation>> {
        return apiCall { service.getNavigationList() }
    }

    override suspend fun getAuthorTitleList(): ApiResponse<List<Classify>> {
        return apiCall { service.getAuthorTitleList() }
    }

    override suspend fun getAuthorArticlePageList(
        authorId: Int,
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { service.getAuthorArticlePageList(authorId, pageNo, pageSize) }
    }

    override suspend fun getUserIntegral(): ApiResponse<Integral> {
        return apiCall { service.getUserIntegral() }
    }

    override suspend fun getIntegralRankPageList(pageNo: Int): ApiResponse<PageResponse<Integral>> {
        return apiCall { service.getIntegralRankPageList(pageNo) }
    }

    override suspend fun getIntegralRecordPageList(pageNo: Int): ApiResponse<PageResponse<IntegralRecord>> {
        return apiCall { service.getIntegralRecordPageList(pageNo) }
    }
}