package com.btpj.wanandroid.ui.ip

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.btpj.lib_base.data.local.IpManager
import com.btpj.lib_base.ext.showDialog
import com.btpj.lib_base.http.RetrofitManager
import com.btpj.lib_base.utils.KeyboardUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.BaseActivity
import com.btpj.wanandroid.databinding.ActivityChangeIpBinding

/**
 * 切换Ip的界面
 *
 * @author LTP 2019/8/5
 */
class ChangeIpActivity :
    BaseActivity<ChangeIpViewModel, ActivityChangeIpBinding>(R.layout.activity_change_ip) {

    companion object {

        /** 页面跳转 */
        fun launch(context: Context) {
            context.startActivity(Intent(context, ChangeIpActivity::class.java))
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@ChangeIpActivity)
                adapter = IpListAdapter(RetrofitManager.BASE_URL).apply {
                    setList(mViewModel.ipSet.value)
                    setOnItemClickListener { _, _, position ->
                        mViewModel.ipAndPort.value = data[position]
                    }
                }
            }

            btnSure.setOnClickListener {
                val ipAndPort = mViewModel.ipAndPort.value!!
                showDialog(
                    "确定要将IP和端口以及地址更改为${ipAndPort}并重启软件吗?",
                    "更改IP需要重启软件",
                    positiveAction = {
                        IpManager.saveDefaultIP(ipAndPort)
                        mViewModel.ipSet.value!!.add(ipAndPort)
                        IpManager.saveIPSet(mViewModel.ipSet.value!!)
                        KeyboardUtil.hideSoftInput(this@ChangeIpActivity, mBinding.etIpAndPort)
                        mViewModel.restartApp()
                    })
            }
        }
    }
}
