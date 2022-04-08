package com.btpj.wanandroid.data.local

import com.tencent.mmkv.MMKV

/**
 * 服务器地址管理
 * 这个项目用不到,放在这里提供一个模板
 *
 * @author LTP 2022/4/8
 */
object IPManager {

    /** 常用的IP */
    private const val DEFAULT_IP_ADDRESS_REMOTE = "chengwenking.yicp.io:53470"     // 外网
    private const val DEFAULT_IP_ADDRESS_LU = "172.168.111.75:8095"     // 小鹿
    private const val DEFAULT_IP_ADDRESS_CHEN = "172.168.111.194:8095"     // 程文
    private const val DEFAULT_IP_ADDRESS_LOCAL = "172.168.111.60:8095"     // ltp

    private const val KEY_IP_SET = "data_ip_set"
    private const val KEY_DEFAULT_IP_AND_PORT = "data_default_ip_and_port"

    private val mmkv by lazy { MMKV.defaultMMKV() }

    /**
     * 保存默认IP
     *
     * @param ip 要保存为默认的IP
     */
    fun saveDefaultIP(ip: String) {
        mmkv.encode(KEY_DEFAULT_IP_AND_PORT, ip)
    }

    /** 获取默认IP */
    fun getDefaultIP(): String {
        return mmkv.decodeString(KEY_DEFAULT_IP_AND_PORT, DEFAULT_IP_ADDRESS_REMOTE)!!
    }

    /** 存储使用过的IP集 */
    fun saveIPSet(ipSet: MutableSet<String>) {
        mmkv.encode(KEY_IP_SET, ipSet)
    }

    /** 获取使用过的IP集 */
    fun getIPSet(): MutableSet<String> {
        return mmkv.decodeStringSet(
            KEY_IP_SET, setOf(
                DEFAULT_IP_ADDRESS_REMOTE, DEFAULT_IP_ADDRESS_LU,
                DEFAULT_IP_ADDRESS_CHEN, DEFAULT_IP_ADDRESS_LOCAL
            )
        )!!
    }
}