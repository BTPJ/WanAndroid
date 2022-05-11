package com.btpj.module_mine.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.btpj.lib_base.http.RetrofitManager
import com.btpj.lib_base.utils.AppUtil
import com.btpj.lib_base.utils.CacheUtil
import com.btpj.lib_base.base.App
import com.btpj.lib_base.base.BaseVMBActivity
import com.btpj.lib_base.data.bean.Banner
import com.btpj.lib_base.data.local.UserManager
import com.btpj.lib_base.export.ModuleWebApi
import com.btpj.lib_base.ext.showDialog
import com.btpj.module_mine.R
import com.btpj.module_mine.databinding.MineActivitySettingBinding

/**
 * 设置
 *
 * @author LTP 2022/4/11
 */
class SettingActivity :
    BaseVMBActivity<SettingViewModel, MineActivitySettingBinding>(R.layout.mine_activity_setting) {

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
                showDialog("确定清理缓存吗?", positiveButtonText = "清理", positiveAction = {
                    CacheUtil.clearAllCache(this@SettingActivity)
                    tvCacheNum.text = CacheUtil.getTotalCacheSize(this@SettingActivity)
                })
            }

            tvVersionName.text = AppUtil.getAppVersionName(this@SettingActivity)
            layerVersion.setOnClickListener { mViewModel.checkAppUpdate(true) }

            // 作者
            layerAuthor.setOnClickListener {
                showDialog(
                    "Q\tQ：1069113473\n\n微信：BTPJ1314\n\n邮箱：1069113473@qq.com",
                    "联系作者", negativeButtonText = ""
                )
            }

            // 项目源码
            tvSourceCode.setOnClickListener {
                ModuleWebApi.navToWebActivity(
                    Banner(
                        title = "项目源码",
                        url = "https://gitee.com/BTPJ_git/WanAndroid"
                    )
                )
            }

            // 退出登录
            btnLogout.setOnClickListener {
                showDialog("确定退出登录吗?", positiveButtonText = "退出", positiveAction = {
                    // 手动清除cookie
                    RetrofitManager.cookieJar.clear()
                    UserManager.logout()
                    App.appViewModel.userEvent.value = null
                    onBackPressed()
                })
            }
        }
    }

}