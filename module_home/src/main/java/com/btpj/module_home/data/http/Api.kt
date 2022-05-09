package com.btpj.module_home.data.http

import com.btpj.lib_base.data.bean.*
import retrofit2.http.*

/**
 * Http接口，Retrofit的请求Service
 *
 * @author LTP  2022/3/21
 */
interface Api {

    /** 获取首页banner数据 */
    @GET("banner/json")
    suspend fun getBanner(): ApiResponse<List<Banner>>

    /** 获取置顶文章集合数据 */
    @GET("article/top/json")
    suspend fun getArticleTopList(): ApiResponse<List<Article>>

    /** 获取首页文章数据 */
    @GET("article/list/{pageNo}/json")
    suspend fun getArticlePageList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): ApiResponse<PageResponse<Article>>

    /** 收藏站内文章 */
    @POST("lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): ApiResponse<Any?>

    /** 取消收藏站内文章 */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id") id: Int): ApiResponse<Any?>

    /** 获取其他作者分享的文章分页列表 */
    @GET("user/{id}/share_articles/{page}/json")
    suspend fun getOtherAuthorArticlePageList(
        @Path("id") id: Int,
        @Path("page") page: Int
    ): ApiResponse<OtherAuthor>

    /** 获取热门搜索数据 */
    @GET("hotkey/json")
    suspend fun getHotSearchList(): ApiResponse<List<HotSearch>>

    /** 根据关键词搜索数据 */
    @POST("article/query/{pageNo}/json")
    suspend fun getSearchDataByKey(
        @Path("pageNo") pageNo: Int,
        @Query("k") searchKey: String
    ): ApiResponse<PageResponse<Article>>
}