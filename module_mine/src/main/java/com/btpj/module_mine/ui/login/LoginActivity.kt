package com.btpj.module_mine.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.btpj.lib_base.base.BaseVMBActivity
import com.btpj.lib_base.export.ModuleMineApi
import com.btpj.lib_base.ext.hideLoading
import com.btpj.lib_base.ext.showLoading
import com.btpj.module_mine.ui.ip.ChangeIpActivity
import com.btpj.module_mine.ui.login.register.RegisterActivity
import com.btpj.module_mine.R
import com.btpj.module_mine.databinding.MineActivityLoginBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 登录
 *
 * @author LTP 2022/4/8
 */
@Route(path = ModuleMineApi.ROUTER_MINE_LOGIN_ACTIVITY)
class LoginActivity :
    BaseVMBActivity<LoginViewModel, MineActivityLoginBinding>(R.layout.mine_activity_login) {

    override fun initView(savedInstanceState: Bundle?) {
        // Activity Results需要先注册
        val registerForActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    mViewModel.userName.set(it.data?.getStringExtra(RegisterActivity.EXTRA_RESULT_USER_NAME))
                }
            }

        mBinding.apply {
            tvRegister.setOnClickListener {
                // 使用Activity Results API
                registerForActivityResult.launch(RegisterActivity.newIntent(this@LoginActivity))
            }

            btnLogin.setOnClickListener {
                showLoading("登录中...")
                mViewModel.login(mViewModel.userName.get()!!, mViewModel.userPwd.get()!!) {
                    hideLoading()
                    onBackPressed()
                }
            }

            ivLogo.setOnClickListener {
                launchChangeIpActivity()
            }
        }
    }

    /** 点击次数 */
    private var mClickTimes: Int = 0

    /** 2S内连续点击5次打开IP选择框 */
    private fun launchChangeIpActivity() {
        lifecycleScope.launch {
            delay(2000)
            // 2s后重置mClickTimes为0
            mClickTimes = 0
        }

        mClickTimes++
        if (mClickTimes > 4) {
            ChangeIpActivity.launch(this)
        }
    }
}