package com.btpj.wanandroid.ui.setting

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.btpj.lib_base.base.BaseVMBActivity
import com.btpj.lib_base.http.RetrofitManager
import com.btpj.lib_base.utils.AppUtil
import com.btpj.lib_base.utils.CacheUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.local.UserManager
import com.btpj.wanandroid.databinding.ActivitySettingBinding
import com.btpj.wanandroid.ui.web.WebActivity

/**
 * 设置
 *
 * @author LTP 2022/4/11
 */
class SettingActivity :
    BaseVMBActivity<SettingViewModel, ActivitySettingBinding>(R.layout.activity_setting) {

    companion object {
        /**
         * 页面启动
         * @param context Context
         */
        fun launch(context: Context) {
            context.startActivity(Intent(context, SettingActivity::class.java))
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.apply {
            tvCacheNum.text = CacheUtil.getTotalCacheSize(this@SettingActivity)
            // 清理缓存
            layerClearCache.setOnClickListener {
                AlertDialog.Builder(this@SettingActivity)
                    .setTitle("温馨提示")
                    .setMessage("确定清理缓存吗")
                    .setNegativeButton("取消") { _, _ -> }
                    .setPositiveButton(
                        "清理"
                    ) { _, _ ->
                        CacheUtil.clearAllCache(this@SettingActivity)
                        tvCacheNum.text = CacheUtil.getTotalCacheSize(this@SettingActivity)
                    }
                    .create()
                    .show()
            }

            tvVersionName.text = AppUtil.getAppVersionName(this@SettingActivity)

            // 作者
            layerAuthor.setOnClickListener {
                AlertDialog.Builder(this@SettingActivity)
                    .setTitle("联系作者")
                    .setMessage("Q\tQ：1069113473\n\n微信：BTPJ1314\n\n邮箱：1069113473@qq.com")
                    .setPositiveButton(
                        "确定"
                    ) { _, _ -> }
                    .create()
                    .show()
            }

            // 项目源码
            tvSourceCode.setOnClickListener {
                WebActivity.launch(this@SettingActivity, "https://gitee.com/BTPJ_git/WanAndroid")
            }

            // 退出登录
            btnLogout.setOnClickListener {
                AlertDialog.Builder(this@SettingActivity)
                    .setTitle("温馨提示")
                    .setMessage("确定退出登录吗")
                    .setNegativeButton("取消") { _, _ -> }
                    .setPositiveButton(
                        "退出"
                    ) { _, _ ->
                        // 手动清除cookie
                        RetrofitManager.cookieJar.clear()
                        UserManager.logout()
                        onBackPressed()
                    }
                    .create()
                    .show()
            }
        }
    }

}