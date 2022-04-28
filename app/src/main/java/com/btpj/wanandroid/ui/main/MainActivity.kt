package com.btpj.wanandroid.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.btpj.lib_base.utils.ToastUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.BaseActivity
import com.btpj.wanandroid.databinding.ActivityMainBinding
import com.btpj.wanandroid.ext.clearLongClickToast
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
    BaseActivity<MainViewModel, ActivityMainBinding>(R.layout.activity_main) {

    companion object {
        /** 记录修改配置（如页面旋转）前navBar的位置的常量 */
        private const val CURRENT_NAV_POSITION = "currentNavPosition"

        /** 跳转 */
        fun launch(context: Context?) {
            context?.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private val mHomeFragment by lazy { HomeFragment.newInstance() }
    private val mProjectFragment by lazy { ProjectFragment.newInstance() }
    private val mSquareFragment by lazy { SquareFragment.newInstance() }
    private val mWechatFragment by lazy { WechatFragment.newInstance() }
    private val mMineFragment by lazy { MineFragment.newInstance() }

    /** 当前显示的Fragment(默认开始为首页) */
    private var mCurrentFragment: Fragment = mHomeFragment
    private var mCurrentNavPosition = 0

    override fun initView(savedInstanceState: Bundle?) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, mHomeFragment, "HomeFragment")
            .commitAllowingStateLoss()

        // 修改配置时（例如页面旋转、深浅模式切换等）的页面恢复
        if (savedInstanceState != null) {
            when (savedInstanceState.getInt(CURRENT_NAV_POSITION)) {
                0 -> R.id.menu_home
                1 -> R.id.menu_project
                2 -> R.id.menu_square
                3 -> R.id.menu_wechat
                else -> R.id.menu_mine
            }.let {
                mBinding.bottomNavigationView.selectedItemId = it
                onNavBarItemSelected(it)
            }
        }

        // 导航Tab
        mBinding.bottomNavigationView.apply {
            // 处理bottomNavigationView的item长按出现Toast的问题
            clearLongClickToast(
                mutableListOf(
                    R.id.menu_home,
                    R.id.menu_project,
                    R.id.menu_square,
                    R.id.menu_wechat,
                    R.id.menu_mine
                )
            )

            setOnItemSelectedListener {
                return@setOnItemSelectedListener onNavBarItemSelected(it.itemId)
            }
        }
    }

    /**
     * bottomNavigationView切换时调用的方法
     *
     * @param itemId 切换Item的id
     */
    private fun onNavBarItemSelected(itemId: Int): Boolean {
        when (itemId) {
            R.id.menu_home -> {
                mCurrentNavPosition = 0
                switchFragment(mHomeFragment)
                return true
            }
            R.id.menu_project -> {
                mCurrentNavPosition = 1
                switchFragment(mProjectFragment)
                return true
            }
            R.id.menu_square -> {
                mCurrentNavPosition = 2
                switchFragment(mSquareFragment)
                return true
            }
            R.id.menu_wechat -> {
                mCurrentNavPosition = 3
                switchFragment(mWechatFragment)
                return true
            }
            else -> {
                mCurrentNavPosition = 4
                switchFragment(mMineFragment)
                return true
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

    override fun onSaveInstanceState(outState: Bundle) {
        // 解决Fragment界面重叠问题,直接传递一个空的Bundle 参考http://blog.csdn.net/showdy/article/details/50825800
        super.onSaveInstanceState(Bundle())
        // 记录屏幕旋转/更换深浅模式等之前切换的Fragment的位置
        outState.putInt(CURRENT_NAV_POSITION, mCurrentNavPosition)
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