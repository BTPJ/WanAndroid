package com.btpj.module_main.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.btpj.lib_base.base.BaseVMBActivity
import com.btpj.module_main.R
import com.btpj.module_main.databinding.MainActivitySplashBinding
import com.btpj.module_main.ui.main.MainActivity
import com.btpj.module_main.data.local.CacheManager
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener

/**
 * 启动页
 *
 * @author LTP 2022/4/20
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity :
    BaseVMBActivity<SplashViewModel, MainActivitySplashBinding>(R.layout.main_activity_splash) {

    private val mBannerList =
        listOf(
            com.btpj.lib_base.R.drawable.ic_splash,
            com.btpj.lib_base.R.drawable.ic_splash2,
            com.btpj.lib_base.R.drawable.ic_splash3
        )

    private val mAdapter by lazy { SplashBannerAdapter(mBannerList) }

    override fun initView(savedInstanceState: Bundle?) {
        window.setBackgroundDrawable(null)
        mBinding.apply {
            if (mViewModel.isFirstUse.get()) {
                clRoot.background = null
                banner.apply {
                    setAdapter(mAdapter)

                    indicator = CircleIndicator(context)
                    addBannerLifecycleObserver(this@SplashActivity)
                    addOnPageChangeListener(object : OnPageChangeListener {
                        override fun onPageScrolled(
                            position: Int,
                            positionOffset: Float,
                            positionOffsetPixels: Int
                        ) {
                        }

                        override fun onPageScrollStateChanged(state: Int) {}

                        override fun onPageSelected(position: Int) {
                            btnEnter.visibility =
                                if (position == mBannerList.size - 1) View.VISIBLE else View.GONE
                        }
                    })
                }

                btnEnter.setOnClickListener {
                    CacheManager.saveFirstUse(false)
                    MainActivity.launch(this@SplashActivity)
                    finish()
                }

            } else {
                MainActivity.launch(this@SplashActivity)
                finish()
            }
        }
    }

    override fun setFullScreen(): Boolean {
        return true
    }
}