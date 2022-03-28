package com.btpj.wanandroid.data.bean

/**
 * 项目标题实体
 *
 * @author LTP  2022/3/25
 */
data class ProjectTitle(
    val author: String,
    val children: List<Any>,
    val courseId: Int,
    val cover: String,
    val desc: String,
    val id: Int,
    val lisense: String,
    val lisenseLink: String,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)