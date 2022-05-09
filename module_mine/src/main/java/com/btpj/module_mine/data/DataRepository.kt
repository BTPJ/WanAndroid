package com.btpj.module_mine.data

import com.btpj.lib_base.data.bean.*
import com.btpj.lib_base.http.BaseRepository
import com.btpj.lib_base.http.RetrofitManager
import com.btpj.lib_base.data.bean.CoinInfo
import com.btpj.lib_base.data.bean.IntegralRecord
import com.btpj.lib_base.data.bean.Share
import com.btpj.module_mine.data.http.Api

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

    override suspend fun getUserIntegral(): ApiResponse<CoinInfo> {
        return apiCall { service.getUserIntegral() }
    }

    override suspend fun getIntegralRankPageList(pageNo: Int): ApiResponse<PageResponse<CoinInfo>> {
        return apiCall { service.getIntegralRankPageList(pageNo) }
    }

    override suspend fun getIntegralRecordPageList(pageNo: Int): ApiResponse<PageResponse<IntegralRecord>> {
        return apiCall { service.getIntegralRecordPageList(pageNo) }
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

    override suspend fun getCollectArticlePageList(pageNo: Int): ApiResponse<PageResponse<CollectArticle>> {
        return apiCall { service.getCollectArticlePageList(pageNo) }
    }

    override suspend fun unCollectArticle(id: Int): ApiResponse<Any?> {
        return apiCall { service.unCollectArticle(id) }
    }
}