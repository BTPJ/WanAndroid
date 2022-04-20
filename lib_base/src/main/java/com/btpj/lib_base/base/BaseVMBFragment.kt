package com.btpj.lib_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.btpj.lib_base.BR
import com.btpj.lib_base.R
import com.btpj.lib_base.ext.hideLoading
import com.btpj.lib_base.utils.LogUtil
import com.btpj.lib_base.utils.ToastUtil
import java.lang.reflect.ParameterizedType
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 封装了ViewModel和DataBinding的Fragment基类
 *
 * @author LTP  2021/11/23
 */
abstract class BaseVMBFragment<VM : BaseViewModel, B : ViewDataBinding>(private val contentViewResId: Int) :
    Fragment() {

    /** 是否第一次加载 */
    private var mIsFirstLoading = true

    protected lateinit var mViewModel: VM
    protected lateinit var mBinding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, contentViewResId, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsFirstLoading = true
        initViewModel()
        initView()
        setupDataBinding()
        createObserve()
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

    /** DataBinding相关设置 */
    private fun setupDataBinding() {
        mBinding.apply {
            // 需绑定lifecycleOwner到Fragment,xml绑定的数据才会随着liveData数据源的改变而改变
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, mViewModel)
        }
    }

    /** View相关初始化 */
    abstract fun initView()

    override fun onResume() {
        super.onResume()
        if (lifecycle.currentState == Lifecycle.State.STARTED && mIsFirstLoading) {
            lazyLoadData()
            mIsFirstLoading = false
        }
    }

    /** 数据懒加载 */
    open fun lazyLoadData() {}

    /** 提供编写LiveData监听逻辑的方法 */
    open fun createObserve() {     // 全局服务器请求错误监听
        mViewModel.apply {
            exception.observe(viewLifecycleOwner) {
                requestError(it.message)
                LogUtil.e("网络请求错误：${it.message}")
                when (it) {
                    is SocketTimeoutException -> ToastUtil.showShort(
                        requireContext(),
                        getString(R.string.request_time_out)
                    )
                    is ConnectException, is UnknownHostException -> ToastUtil.showShort(
                        requireContext(),
                        getString(R.string.network_error)
                    )
                    else -> ToastUtil.showShort(
                        requireContext(), it.message ?: getString(R.string.response_error)
                    )
                }
            }

            // 全局服务器返回的错误信息监听
            errorResponse.observe(viewLifecycleOwner) {
                requestError(it?.errorMsg)
                it?.errorMsg?.run {
                    ToastUtil.showShort(requireContext(), this)
                }
            }
        }
    }

    /** 提供一个请求错误的方法,用于像关闭加载框之类的 */
    open fun requestError(msg: String? = null) {
        hideLoading()
    }
}