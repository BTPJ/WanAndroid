package com.btpj.wanandroid.ui.main.setting

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.btpj.lib_base.base.BaseVMBActivity
import com.btpj.lib_base.utils.CacheUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.local.UserManager
import com.btpj.wanandroid.databinding.ActivitySettingBinding

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
            // 清理缓存
            tvCacheNum.apply {
                text = CacheUtil.getTotalCacheSize(this@SettingActivity)
                setOnClickListener {
                    AlertDialog.Builder(this@SettingActivity)
                        .setTitle("温馨提示")
                        .setMessage("确定清理缓存吗")
                        .setNegativeButton("取消") { _, _ -> }
                        .setPositiveButton(
                            "清理"
                        ) { _, _ ->
                            CacheUtil.clearAllCache(this@SettingActivity)
                            this.text = CacheUtil.getTotalCacheSize(this@SettingActivity)
                        }
                        .create()
                        .show()
                }
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
                        UserManager.logout()
                        onBackPressed()
                    }
                    .create()
                    .show()
            }
        }
    }

}