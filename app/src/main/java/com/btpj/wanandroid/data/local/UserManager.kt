package com.btpj.wanandroid.data.local

import com.btpj.wanandroid.data.bean.User
import com.tencent.mmkv.MMKV

/**
 * 数据管理类
 *
 * @author LTP  17/8/25.
 */
object UserManager {

    /** MMKV独有的mmapId */
    private const val MMKV_MAP_ID = "user"

    /** 保存登录成功的用户的Json串的KEY */
    private const val KEY_USER = "user_data"

    /** 保存最后一次登录成功的用户名的KEY */
    private const val KEY_LAST_USER_NAME = "lastUserName"
    private const val KEY_LAST_USER_PASSWORD = "lastUserPassword"

    /** 保存登录成功的用户Token */
    private const val KEY_TOKEN = "token"

    private val mmkv by lazy { MMKV.mmkvWithID(MMKV_MAP_ID) }

    /**
     * 存储用户到本地
     *
     * @param user    用户
     */
    fun saveUser(user: User) {
        mmkv.encode(KEY_USER, user)
    }

    /**
     * 获取存储的本地用户信息
     *
     * @return 本地用户信息
     */
    fun getUser(): User? {
        return mmkv.decodeParcelable(KEY_USER, User::class.java)
    }


    /**
     * 是否已登录(自动登录的判断)
     *
     * @return 是否已登录
     */
    fun isLogin(): Boolean {
        return getUser() != null
    }

    /**
     * 存储用户token到本地
     *
     * @param token    Token
     */
    fun saveToken(token: String?) {
        mmkv.encode(KEY_TOKEN, token)
    }

    /**
     * 获取登录用户Token信息
     *
     * @return 登录Token
     */
    fun getToken(): String? {
        return mmkv.decodeString(KEY_TOKEN)
    }


    /**
     * 存储上一次登录成功的用户名(退出时显示在登录名里)
     *
     * @param userName 用户名
     */
    fun saveLastUserName(userName: String) {
        mmkv.encode(KEY_LAST_USER_NAME, userName)
    }

    /**
     * 获取上一次登录成功的用户名(退出时显示在登录名里)
     *
     * @return 上一次登录成功的用户名
     */
    fun getLastUserName(): String {
        return mmkv.decodeString(KEY_LAST_USER_NAME, "")!!
    }

    /**
     * 存储上一次登录成功的用户密码(自动登录)
     *
     * @param password 密码
     */
    fun storeLastUserPwd(password: String) {
        mmkv.encode(KEY_LAST_USER_PASSWORD, password)
    }


    /**
     * 获取上一次登录成功的用户密码(自动登录)
     *
     * @return 上一次登录成功的用户密码
     */
    fun getLastUserPassword(): String? {
        return mmkv.decodeString(KEY_LAST_USER_PASSWORD)
    }

    /**
     * 退出登录（清空所有用户数据）
     */
    fun logout() {
        // 清除登录信息
        mmkv.remove(KEY_USER)
        mmkv.remove(KEY_LAST_USER_PASSWORD)
        mmkv.remove(KEY_TOKEN)
    }
}
