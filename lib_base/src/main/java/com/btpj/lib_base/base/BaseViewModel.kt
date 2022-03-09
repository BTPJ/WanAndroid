package com.btpj.lib_base.base

import androidx.lifecycle.ViewModel

/**
 * ViewModel基类
 * @author LTP  2021/11/23
 */
abstract class BaseViewModel : ViewModel() {

    /** 界面启动时要进行的初始化逻辑，如网络请求,数据初始化等 */
    abstract fun start()
}