package com.btpj.wanandroid.base

import androidx.databinding.ViewDataBinding
import com.btpj.lib_base.base.BaseVMBFragment
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.utils.LogUtil
import com.btpj.wanandroid.ui.login.LoginActivity

/**
 * 继承自BaseVMBFragment
 * 在这里只做了统一处理跳转登录页面的逻辑
 *
 * @author LTP  2021/11/23
 */
abstract class BaseFragment<VM : BaseViewModel, B : ViewDataBinding>(contentViewResId: Int) :
    BaseVMBFragment<VM, B>(contentViewResId) {

    override fun createObserve() {
        super.createObserve()
        mViewModel.errorResponse.observe(viewLifecycleOwner) {
            LogUtil.d("空间和空间")
            if (it?.errorCode == -1001) { // 需要登录
                LoginActivity.launch(requireContext())
                LogUtil.d("空间和空间1")
            }
        }
    }
}