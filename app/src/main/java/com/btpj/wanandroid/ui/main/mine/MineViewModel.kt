package com.btpj.wanandroid.ui.main.mine

import androidx.databinding.ObservableField
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.wanandroid.data.bean.User
import com.btpj.wanandroid.data.local.UserManager

class MineViewModel : BaseViewModel() {
    val user = ObservableField<User>()

    val userName = object : ObservableField<String>(user) {
        override fun get(): String {
            return if (UserManager.isLogin()) user.get()!!.nickname else "请登录"
        }
    }

//    val info = object : ObservableField<String>(user) {
//        override fun get(): String {
//            return if (CacheManager.isLogin()) "id：${user.get()!!.id} 排名：${user.get().chapterTops}" else "id：-- 排名：--"
//        }
//    }

    override fun start() {
        if (UserManager.isLogin()) {
            user.set(UserManager.getUser()!!)
        }
    }
}