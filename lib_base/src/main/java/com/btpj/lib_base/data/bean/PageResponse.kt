package com.btpj.lib_base.data.bean

/**
 * 分页实体
 *
 * @author LTP  2022/3/22
 */
data class PageResponse<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)