package com.btpj.wanandroid.data.http

import com.btpj.lib_base.data.bean.ApiResponse
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.wanandroid.data.bean.*
import retrofit2.http.*

/**
 * Http接口，Retrofit的请求Service
 *
 * @author LTP  2022/3/21
 */
interface Api {

    /** 登录 */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") pwd: String
    ): ApiResponse<User>

    /** 注册 */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") pwd: String,
        @Field("repassword") pwdSure: String
    ): ApiResponse<Any?>

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

    /** 获取项目分类数据 */
    @GET("project/tree/json")
    suspend fun getProjectTitleList(): ApiResponse<List<ProjectTitle>>

    /** 获取最新项目列表分页数据 */
    @GET("article/listproject/{pageNo}/json")
    suspend fun getNewProjectPageList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): ApiResponse<PageResponse<Article>>

    /** 获取项目列表分页数据 */
    @GET("project/list/{pageNo}/json")
    suspend fun getProjectPageList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int,
        @Query("cid") categoryId: Int
    ): ApiResponse<PageResponse<Article>>

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
    suspend fun getTreeList(): ApiResponse<List<Structure>>

    /** 获取导航数据 */
    @GET("navi/json")
    suspend fun getNavigationList(): ApiResponse<List<Navigation>>

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

    /** 获取个人积分 */
    @GET("lg/coin/userinfo/json")
    suspend fun getUserIntegral(): ApiResponse<CoinInfo>

    /** 获取积分排行列表分页 */
    @GET("coin/rank/{pageNo}/json")
    suspend fun getIntegralRankPageList(@Path("pageNo") pageNo: Int): ApiResponse<PageResponse<CoinInfo>>

    /** 获取积分历史 */
    @GET("lg/coin/list/{pageNo}/json")
    suspend fun getIntegralRecordPageList(@Path("pageNo") pageNo: Int): ApiResponse<PageResponse<IntegralRecord>>

    /** 获取收藏文章分页列表 */
    @GET("lg/collect/list/{pageNo}/json")
    suspend fun getCollectArticlePageList(@Path("pageNo") pageNo: Int): ApiResponse<PageResponse<CollectArticle>>

    /** 获取收藏网址列表 */
    @GET("lg/collect/usertools/json")
    suspend fun getCollectUrlList(): ApiResponse<List<CollectUrl>>

    /** 获取我分享的文章分页列表 */
    @GET("user/lg/private_articles/{pageNo}/json")
    suspend fun getMyShareArticlePageList(@Path("pageNo") pageNo: Int): ApiResponse<Share>

    /** 添加要分享的文章 */
    @POST("lg/user_article/add/json")
    @FormUrlEncoded
    suspend fun addArticle(
        @Field("title") title: String,
        @Field("link") link: String
    ): ApiResponse<Any?>

    /** 删除自己分享的文章 */
    @POST("lg/user_article/delete/{id}/json")
    suspend fun deleteShareArticle(@Path("id") id: Int): ApiResponse<Any?>

    /** 收藏网址 */
    @POST("lg/collect/addtool/json")
    suspend fun collectUrl(
        @Query("name") name: String,
        @Query("link") link: String
    ): ApiResponse<CollectUrl?>

    /** 取消收藏站内文章 */
    @POST("lg/collect/deletetool/json")
    suspend fun unCollectUrl(@Query("id") id: Int): ApiResponse<Any?>

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