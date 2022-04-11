package com.btpj.wanandroid.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.btpj.lib_base.base.BaseVMBActivity
import com.btpj.wanandroid.R
import com.btpj.wanandroid.databinding.ActivityLoginBinding

/**
 * 登录
 *
 * @author LTP 2022/4/8
 */
class LoginActivity :
    BaseVMBActivity<LoginViewModel, ActivityLoginBinding>(R.layout.activity_login) {

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
        mBinding.apply {
            btnLogin.setOnClickListener {
                mViewModel.login(mViewModel.userName.get()!!, mViewModel.userPwd.get()!!)
            }
        }
    }

    override fun createObserve() {
        super.createObserve()
        mViewModel.apply {
            user.observe(this@LoginActivity) {
                onBackPressed()
            }
        }
    }
}