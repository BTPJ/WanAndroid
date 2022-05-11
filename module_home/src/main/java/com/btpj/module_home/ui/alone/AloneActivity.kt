package com.btpj.module_home.ui.alone

import android.os.Bundle
import com.btpj.lib_base.R
import com.btpj.lib_base.base.BaseVMBActivity
import com.btpj.lib_base.databinding.CommonContainerBinding
import com.btpj.lib_base.export.ModuleHomeApi

/**
 * 作为Application时的测试页面
 *
 * @author LTP 2022/4/8
 */
class AloneActivity :
    BaseVMBActivity<AloneViewModel, CommonContainerBinding>(R.layout.common_container) {

    override fun initView(savedInstanceState: Bundle?) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, ModuleHomeApi.getHomeFragment())
            .commit()
    }
}