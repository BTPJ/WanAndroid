package com.btpj.module_square.ui.alone

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.btpj.lib_base.base.BaseVMBActivity
import com.btpj.lib_base.export.ModuleMineApi
import com.btpj.lib_base.R
import com.btpj.lib_base.databinding.CommonContainerBinding
import com.btpj.lib_base.export.ModuleSquareApi

/**
 * 作为Application时的测试页面
 *
 * @author LTP 2022/4/8
 */
@Route(path = ModuleMineApi.ROUTER_MINE_LOGIN_ACTIVITY)
class AloneActivity :
    BaseVMBActivity<AloneViewModel, CommonContainerBinding>(R.layout.common_container) {

    override fun initView(savedInstanceState: Bundle?) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, ModuleSquareApi.getSquareFragment())
            .commit()
    }
}