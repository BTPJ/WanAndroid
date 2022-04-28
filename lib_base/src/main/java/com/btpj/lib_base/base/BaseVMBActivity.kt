package com.btpj.lib_base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.btpj.lib_base.BR
import com.btpj.lib_base.R
import com.btpj.lib_base.ext.hideLoading
import com.btpj.lib_base.utils.LogUtil
import com.btpj.lib_base.utils.StatusBarUtil
import com.btpj.lib_base.utils.ToastUtil
import java.lang.reflect.ParameterizedType
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 封装了ViewModel和DataBinding的Activity基类
 *
 * @author LTP  2021/11/23
 */
abstract class BaseVMBActivity<VM : BaseViewModel, B : ViewDataBinding>(private val contentViewResId: Int) :
    AppCompatActivity() {

    lateinit var mViewModel: VM
    lateinit var mBinding: B

    /** 是否是无状态栏的全屏模式 */
    open fun setFullScreen(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置沉浸式状态栏，由于启动页SplashActivity需要无状态栏，这里写死不太好
        // 直接在主题里将其他的状态栏颜色写成跟ActionBar相同，而启动页则是无状态栏
        // 或者提供一个修改的api让SplashActivity重写，两者均可（假如需要更换主题用代码设置更灵活）
        if (setFullScreen()) {
            StatusBarUtil.setNoStatus(this)
        } else {
            StatusBarUtil.setImmersionStatus(this)
        }

        initViewModel()
        initDataBinding()
        createObserve()
        initView(savedInstanceState)
    }

    /** ViewModel初始化 */
    @Suppress("UNCHECKED_CAST")
    private fun initViewModel() {
        // 这里利用反射获取泛型中第一个参数ViewModel
        val type: Class<VM> =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this).get(type)
        mViewModel.start()
    }

    /** DataBinding初始化 */
    private fun initDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, contentViewResId)
        mBinding.apply {
            // 需绑定lifecycleOwner到activity,xml绑定的数据才会随着liveData数据源的改变而改变
            lifecycleOwner = this@BaseVMBActivity
            setVariable(BR.viewModel, mViewModel)
        }
    }

    /** View相关初始化 */
    abstract fun initView(savedInstanceState: Bundle?)

    /** 提供编写LiveData监听逻辑的方法 */
    open fun createObserve() {
        // 全局服务器请求错误监听
        mViewModel.apply {
            exception.observe(this@BaseVMBActivity) {
                requestError(it.message)
                LogUtil.e("网络请求错误：${it.message}")
                when (it) {
                    is SocketTimeoutException -> ToastUtil.showShort(
                        this@BaseVMBActivity,
                        getString(R.string.request_time_out)
                    )
                    is ConnectException, is UnknownHostException -> ToastUtil.showShort(
                        this@BaseVMBActivity,
                        getString(R.string.network_error)
                    )
                    else -> ToastUtil.showShort(
                        this@BaseVMBActivity, it.message ?: getString(R.string.response_error)
                    )
                }
            }

            // 全局服务器返回的错误信息监听
            errorResponse.observe(this@BaseVMBActivity) {
                requestError(it?.errorMsg)
                it?.errorMsg?.run {
                    ToastUtil.showShort(this@BaseVMBActivity, this)
                }
            }
        }
    }

    /** 提供一个请求错误的方法,用于像关闭加载框,显示错误布局之类的 */
    open fun requestError(msg: String?) {
        hideLoading()
    }
}