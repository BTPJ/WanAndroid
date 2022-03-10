package com.btpj.wanandroid.ui.main

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.btpj.lib_base.base.BaseVMBActivity
import com.btpj.lib_base.utils.ToastUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.databinding.ActivityMainBinding
import com.btpj.wanandroid.ui.main.home.HomeFragment
import com.btpj.wanandroid.ui.main.mine.MineFragment
import com.btpj.wanandroid.ui.main.project.ProjectFragment
import com.btpj.wanandroid.ui.main.square.SquareFragment
import com.btpj.wanandroid.ui.main.wechat.WechatFragment

/**
 * 主页
 *
 * @author LTP 2022/3/9
 */
class MainActivity :
    BaseVMBActivity<MainViewModel, ActivityMainBinding>(R.layout.activity_main) {

    companion object {

        /** 跳转 */
        fun launch(context: Context?) {
            context?.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    /** 当前显示的Fragment(默认开始为首页) */
    private lateinit var mCurrentFragment: Fragment
    private val mHomeFragment by lazy { HomeFragment.newInstance() }
    private val mProjectFragment by lazy { ProjectFragment.newInstance() }
    private val mSquareFragment by lazy { SquareFragment.newInstance() }
    private val mWechatFragment by lazy { WechatFragment.newInstance() }
    private val mMineFragment by lazy { MineFragment.newInstance() }

    override fun initView() {
        mCurrentFragment = mHomeFragment
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, mHomeFragment, "HomeFragment")
            .commitAllowingStateLoss()

        // 导航Tab
        mBinding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    switchFragment(mHomeFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.menu_project -> {
                    switchFragment(mProjectFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.menu_square -> {
                    switchFragment(mSquareFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.menu_wechat -> {
                    switchFragment(mWechatFragment)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    switchFragment(mMineFragment)
                    return@setOnItemSelectedListener true
                }
            }
        }
    }

    /**
     * 切换Fragment
     *
     * @param fragment 要切换的Fragment
     */
    private fun switchFragment(fragment: Fragment) {
        if (fragment !== mCurrentFragment) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            // 先隐藏当前显示的Fragment
            fragmentTransaction.hide(mCurrentFragment)
            if (!fragment.isAdded) {
                // 存入Tag,以便获取，解决界面重叠问题 参考http://blog.csdn.net/showdy/article/details/50825800
                fragmentTransaction.add(R.id.fl_container, fragment, fragment.javaClass.simpleName)
                    .show(fragment)
            } else {
                fragmentTransaction.show(fragment)
            }
            // 执行提交
            fragmentTransaction.commitAllowingStateLoss()
            // 将当前Fragment赋值为切换后的Fragment
            mCurrentFragment = fragment
        }
    }

    /** 上一次点击返回键的时间 */
    private var lastBackMills: Long = 0

    override fun onBackPressed() {
        // 重写返回键监听实现双击退出
        if (System.currentTimeMillis() - lastBackMills > 2000) {
            lastBackMills = System.currentTimeMillis()
            ToastUtil.showShort(this, getString(R.string.toast_double_back_exit))
        } else {
            super.onBackPressed()
        }
    }
}