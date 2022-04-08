package com.btpj.lib_base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.btpj.lib_base.BR
import com.btpj.lib_base.utils.LogUtil
import com.btpj.lib_base.utils.StatusBarUtil
import java.lang.reflect.ParameterizedType

/**
 * 封装了ViewModel和DataBinding的Activity基类
 *
 * @author LTP  2021/11/23
 */
abstract class BaseVMBActivity<VM : BaseViewModel, B : ViewDataBinding>(private val contentViewResId: Int) :
    AppCompatActivity() {

    lateinit var mViewModel: VM
    lateinit var mBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置透明通知栏
        StatusBarUtil.setImmersionStatus(this)
        initViewModel()
        initDataBinding()
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
        createObserve()
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
    open fun createObserve() {}
}