package com.btpj.lib_base.utils

import android.util.Log

/**
 * 日志打印工具类
 *
 * @author LTP  2018/3/26
 */
object LogUtil {

    /** 是否是调试状态，即是否打印日志 */
    private var isDebug = true
    private var tag = "BTPJ"

    /**
     * 设置调试状态（以便实现是否打印日志，可以在application的onCreate函数里面初始化）
     *
     * @param isDebug   true: 调试状态即打印所有日志
     *                false: 上线状态即关闭所有日志的打印
     */
    fun isDebug(isDebug: Boolean) {
        this.isDebug = isDebug
    }

    /**
     * 设置打印日志的Tag
     *
     * @param tag 打印日志的Tag
     */
    fun setTag(tag: String) {
        this.tag = tag
    }

    /**
     * 打印VERBOSE类型的日志
     *
     * @param tag 打印的Tag
     * @param msg 打印的信息
     */
    fun v(tag: String, msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }

    /**
     * 打印VERBOSE类型的日志
     *
     * @param msg 打印的信息
     */
    fun v(msg: String) {
        v(msg)
    }

    /**
     * 打印DEBUG类型的日志
     *
     * @param tag 打印的Tag
     * @param msg 打印的信息
     */
    fun d(tag: String, msg: String) {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }

    /**
     * 打印DEBUG类型的日志
     *
     * @param msg 打印的信息
     */
    fun d(msg: String) {
        d(tag, msg)
    }

    /**
     * 打印INFO类型的日志
     *
     * @param tag 打印的Tag
     * @param msg 打印的信息
     */
    fun i(tag: String, msg: String) {
        if (isDebug) {
            Log.i(tag, msg)
        }
    }

    /**
     * 打印INFO类型的日志
     *
     * @param msg 打印的信息
     */
    fun i(msg: String) {
        i(tag, msg)
    }

    /**
     * 打印WARN类型的日志
     *
     * @param tag 打印的Tag
     * @param msg 打印的信息
     */
    fun w(tag: String, msg: String) {
        if (isDebug) {
            Log.w(tag, msg)
        }
    }

    /**
     * 打印WARN类型的日志
     *
     * @param msg 打印的信息
     */
    fun w(msg: String) {
        w(tag, msg)
    }

    /**
     * 打印ERROR类型的日志
     *
     * @param tag 打印的Tag
     * @param msg 打印的信息
     */
    fun e(tag: String, msg: String) {
        if (isDebug) {
            Log.e(tag, msg)
        }
    }

    /**
     * 打印ERROR类型的日志
     *
     * @param msg 打印的信息
     */
    fun e(msg: String) {
        e(tag, msg)
    }
}