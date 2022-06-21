package com.btpj.wanandroid.data

import com.btpj.lib_base.data.bean.ApiResponse
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.http.BaseRepository
import com.btpj.lib_base.http.RetrofitManager
import com.btpj.wanandroid.data.bean.*
import com.btpj.wanandroid.data.http.Api
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

    override suspend fun register(
        username: String,
        pwd: String,
        pwdSure: String
    ): ApiResponse<Any?> {
        return apiCall { service.register(username, pwd, pwdSure) }
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

    override suspend fun getTreeList(): ApiResponse<List<Structure>> {
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

    override suspend fun getUserIntegral(): ApiResponse<CoinInfo> {
        return apiCall { service.getUserIntegral() }
    }

    override suspend fun getIntegralRankPageList(pageNo: Int): ApiResponse<PageResponse<CoinInfo>> {
        return apiCall { service.getIntegralRankPageList(pageNo) }
    }

    override suspend fun getIntegralRecordPageList(pageNo: Int): ApiResponse<PageResponse<IntegralRecord>> {
        return apiCall { service.getIntegralRecordPageList(pageNo) }
    }

    override suspend fun getCollectArticlePageList(pageNo: Int): ApiResponse<PageResponse<CollectArticle>> {
        return apiCall { service.getCollectArticlePageList(pageNo) }
    }

    override suspend fun getCollectUrlList(): ApiResponse<List<CollectUrl>> {
        return apiCall { service.getCollectUrlList() }
    }

    override suspend fun getMyShareArticlePageList(pageNo: Int): ApiResponse<Share> {
        return apiCall { service.getMyShareArticlePageList(pageNo) }
    }

    override suspend fun addArticle(title: String, link: String): ApiResponse<Any?> {
        return apiCall { service.addArticle(title, link) }
    }

    override suspend fun deleteShareArticle(id: Int): ApiResponse<Any?> {
        return apiCall { service.deleteShareArticle(id) }
    }

    override suspend fun collectUrl(name: String, link: String): ApiResponse<CollectUrl?> {
        return apiCall { service.collectUrl(name, link) }
    }

    override suspend fun unCollectUrl(id: Int): ApiResponse<Any?> {
        return apiCall { service.unCollectUrl(id) }
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