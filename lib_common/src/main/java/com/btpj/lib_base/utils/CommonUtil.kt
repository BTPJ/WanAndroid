package com.btpj.lib_base.utils

import android.graphics.Color
import java.util.*

/**
 * 一些额外的工具类，不好分类的那种
 *
 * @author LTP  2022/4/7
 */
object CommonUtil {

    /** 判断String是否为空或空串，主要提供给xml中dataBinding用 */
    @JvmStatic
    fun isEmpty(str: String?): Boolean {
        return str?.isEmpty() ?: true
    }

    /** 获取随机rgb颜色值 */
    fun randomColor(): Int {
        Random().run {
            //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
            val red = nextInt(190)
            val green = nextInt(190)
            val blue = nextInt(190)
            //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
            return Color.rgb(red, green, blue)
        }
    }
}