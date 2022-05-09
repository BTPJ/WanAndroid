package com.btpj.module_square.data.http

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

    /** 获取广场列表分页数据 */
    @GET("user_article/list/{pageNo}/json")
    suspend fun getSquarePageList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): ApiResponse<PageResponse<Article>>

    /** 获取每日一问列表分页数据 */
    @GET("wenda/list/{pageNo}/json")
    suspend fun getAskPageList(@Path("pageNo") pageNo: Int): ApiResponse<PageResponse<Article>>

    /** 获取体系数据 */
    @GET("tree/json")
    suspend fun getTreeList(): ApiResponse<List<MySystem>>

    /** 获取导航数据 */
    @GET("navi/json")
    suspend fun getNavigationList(): ApiResponse<List<Navigation>>

}