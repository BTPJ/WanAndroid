package com.btpj.wanandroid.ui.login.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.btpj.lib_base.ext.hideLoading
import com.btpj.lib_base.ext.showLoading
import com.btpj.lib_base.utils.ToastUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.BaseActivity
import com.btpj.wanandroid.databinding.ActivityRegisterBinding

/**
 * 注册
 *
 * @author LTP 2022/4/8
 */
class RegisterActivity :
    BaseActivity<RegisterViewModel, ActivityRegisterBinding>(R.layout.activity_register) {

    companion object {

        /** 返回给LoginActivity的extra产量key */
        const val EXTRA_RESULT_USER_NAME = "user_name"

        /**
         * 传递启动的Intent
         * @param context Context
         */
        fun newIntent(context: Context): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.apply {
            btnRegister.setOnClickListener {
                when {
                    mViewModel.userPwd.get()!!.length < 6 ->
                        ToastUtil.showLong(this@RegisterActivity, "密码最少6位")

                    mViewModel.userPwd.get() != mViewModel.userPwdSure.get() ->
                        ToastUtil.showLong(this@RegisterActivity, "密码不一致")

                    else -> {
                        showLoading("注册中...")
                        mViewModel.register(
                            mViewModel.userName.get()!!,
                            mViewModel.userPwd.get()!!,
                            mViewModel.userPwdSure.get()!!,
                        ) {
                            hideLoading()
                            setResult(
                                RESULT_OK,
                                Intent().apply {
                                    putExtra(
                                        EXTRA_RESULT_USER_NAME,
                                        mViewModel.userName.get()
                                    )
                                })
                            finish()
                        }
                    }
                }
            }
        }
    }
}