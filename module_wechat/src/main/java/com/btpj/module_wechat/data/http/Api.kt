package com.btpj.module_wechat.data.http

import com.btpj.lib_base.data.bean.*
import retrofit2.http.*

/**
 * Http接口，Retrofit的请求Service
 *
 * @author LTP  2022/3/21
 */
interface Api {

    /** 收藏站内文章 */
    @POST("lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): ApiResponse<Any?>

    /** 取消收藏站内文章 */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id") id: Int): ApiResponse<Any?>

    /** 获取公众号作者列表 */
    @GET("wxarticle/chapters/json")
    suspend fun getAuthorTitleList(): ApiResponse<List<Classify>>

    /** 获取公众号作者文章分页列表 */
    @GET("wxarticle/list/{authorId}/{pageNo}/json")
    suspend fun getAuthorArticlePageList(
        @Path("authorId") authorId: Int,
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): ApiResponse<PageResponse<Article>>
}