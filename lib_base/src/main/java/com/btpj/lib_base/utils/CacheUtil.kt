package com.btpj.lib_base.utils


import android.content.Context
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.math.BigDecimal

object CacheUtil {
    /**
     * 获取APP缓存大小
     */
    fun getTotalCacheSize(context: Context): String {
        var cacheSize = getFolderSize(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            cacheSize += getFolderSize(context.externalCacheDir)
        }
        return getFormatSize(cacheSize.toDouble())
    }

    /**
     * 清除缓存
     */
    fun clearAllCache(activity: AppCompatActivity?) {
        activity?.let {
            deleteDir(it.cacheDir)
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                if (it.externalCacheDir == null) {
                    ToastUtil.showLong(activity, "清理缓存失败")
                }
                return
            }
            it.externalCacheDir?.let { file ->
                if (deleteDir(file)) {
                    ToastUtil.showLong(activity, "清理缓存成功")
                }
            }
        }
    }

}

/**
 * 删除文件
 *
 * @param file File
 */
private fun deleteDir(file: File): Boolean {
    if (file.isDirectory) {
        val children = file.list()
        for (i in children.indices) {
            val success = deleteDir(File(file, children[i]))
            if (!success) {
                return false
            }
        }
    }
    return file.delete()
}

/**
 * 获取文件
 * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/
 * 目录，一般放一些长时间保存的数据
 * Context.getExternalCacheDir() -->
 * SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
 */
fun getFolderSize(file: File?): Long {
    var size: Long = 0
    file?.run {
        try {
            val fileList = listFiles()
            for (i in fileList.indices) {
                // 如果下面还有文件
                size += if (fileList[i].isDirectory) {
                    getFolderSize(fileList[i])
                } else {
                    fileList[i].length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return size
}

/**
 * 格式化缓存单位
 */
fun getFormatSize(size: Double): String {
    val kiloByte = size / 1024
    if (kiloByte < 1) {
        return size.toString() + "B"
    }
    val megaByte = kiloByte / 1024
    if (megaByte < 1) {
        val result1 = BigDecimal(kiloByte.toString())
        return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB"
    }

    val gigaByte = megaByte / 1024
    if (gigaByte < 1) {
        val result2 = BigDecimal(megaByte.toString())
        return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
    }

    val teraBytes = gigaByte / 1024
    if (teraBytes < 1) {
        val result3 = BigDecimal(gigaByte.toString())
        return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
    }

    val result4 = BigDecimal(teraBytes)
    return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
}