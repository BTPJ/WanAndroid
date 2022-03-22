package com.btpj.wanandroid.data.http

import com.btpj.wanandroid.data.bean.ApiResponse
import com.btpj.wanandroid.data.bean.Banner
import retrofit2.http.GET

/**
 * Http接口，Retrofit的请求Service
 *
 * @author LTP  2022/3/21
 */
interface Api {

    /**
     * 获取首页banner数据
     */
    @GET("banner/json")
    suspend fun getBanner(): ApiResponse<ArrayList<Banner>>
}