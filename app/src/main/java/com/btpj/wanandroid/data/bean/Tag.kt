package com.btpj.wanandroid.data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author LTP  2022/3/22
 */
@Parcelize
data class Tag(
    val name: String,
    val url: String
) : Parcelable