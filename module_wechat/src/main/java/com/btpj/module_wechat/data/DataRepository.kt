package com.btpj.module_wechat.data

import com.btpj.lib_base.data.bean.*
import com.btpj.lib_base.http.BaseRepository
import com.btpj.lib_base.http.RetrofitManager
import com.btpj.module_wechat.data.http.Api

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
}