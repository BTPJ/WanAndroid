package com.btpj.wanandroid.ui.setting

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.BaseApp.Companion.appContext
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.utils.ToastUtil
import com.btpj.wanandroid.data.local.UserManager
import com.tencent.bugly.beta.Beta


/**
 * @author LTP  2022/4/11
 */
class SettingViewModel : BaseViewModel() {
    private val _haveNewVersion: MutableLiveData<Boolean> = MutableLiveData()
    val haveNewVersion = _haveNewVersion

    override fun start() {
        checkAppUpdate()
    }

    /**
     * APP更新检查
     * @param isManual 是否手动检查，默认为false
     */
    fun checkAppUpdate(isManual: Boolean = false) {
        Beta.checkUpgrade(isManual, !isManual)
        // 获取升级信息
        val upgradeInfo = Beta.getUpgradeInfo()
        if (upgradeInfo == null) {
            haveNewVersion.value = false
            if (isManual) {
                ToastUtil.showShort(appContext, "你已经是最新版本")
            }
        } else {
            haveNewVersion.value = true
        }
    }
}