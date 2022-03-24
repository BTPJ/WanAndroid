package com.btpj.lib_base.http

import com.btpj.lib_base.BaseApp.Companion.appContext
import com.btpj.lib_base.http.interceptor.logInterceptor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit管理类
 *
 * @author LTP  2022/3/21
 */
object RetrofitManager {
    /** 请求超时时间 */
    private const val TIME_OUT_SECONDS = 5

    /** 请求IP和端口*/
    const val BASE_URL = "https://www.wanandroid.com"

    /** OkHttpClient相关配置 */
    private val client: OkHttpClient
        get() = OkHttpClient.Builder()
            // 请求过滤器
            .addInterceptor(logInterceptor)
            // 请求超时时间
            .connectTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .cookieJar(
                PersistentCookieJar(
                    SetCookieCache(),
                    SharedPrefsCookiePersistor(appContext)
                )
            )
            .build()

    /**
     * Retrofit相关配置
     */
    fun <T> getService(serviceClass: Class<T>, baseUrl: String? = null): T {
        return Retrofit.Builder()
            .client(client)
            // 使用Moshi更适合Kotlin
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl ?: BASE_URL)
            .build()
            .create(serviceClass)
    }
}