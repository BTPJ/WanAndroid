package com.btpj.wanandroid.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.btpj.wanandroid.base.BaseActivity
import com.btpj.lib_base.ext.hideLoading
import com.btpj.lib_base.ext.showLoading
import com.btpj.wanandroid.R
import com.btpj.wanandroid.databinding.ActivityLoginBinding
import com.btpj.wanandroid.ui.login.register.RegisterActivity

/**
 * 登录
 *
 * @author LTP 2022/4/8
 */
class LoginActivity :
    BaseActivity<LoginViewModel, ActivityLoginBinding>(R.layout.activity_login) {

    companion object {


        /**
         * 页面启动
         * @param context Context
         */
        fun launch(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

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
        }
    }
}