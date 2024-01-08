package com.btpj.wanandroid.ui.ip

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.data.local.IpManager
import com.btpj.wanandroid.base.BaseViewModel
import kotlinx.coroutines.delay
import java.util.TreeSet

/**
 * @author LTP  2019/8/5
 */
class ChangeIpViewModel : BaseViewModel<String>() {

    /** 输入的IP和端口 */
    val ipAndPort: MutableLiveData<String> = MutableLiveData("")

    /** 历史IP列表 */
    val ipSet: MutableLiveData<MutableSet<String>> = MutableLiveData(TreeSet())

    fun start() {
        initIpList()
    }

    /** 初始化IP列表 */
    private fun initIpList() {
        ipSet.value = IpManager.getIPSet()
    }

    /** 重启APP */
    fun restartApp() {
        // 加线程并等待是为了SharedPreference能存储成功
        launch({
            delay(500)
            android.os.Process.killProcess(android.os.Process.myPid())
        })
    }
}