package com.btpj.wanandroid.ui.splash

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.LinearInterpolator
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.BaseActivity
import com.btpj.wanandroid.data.local.CacheManager
import com.btpj.wanandroid.databinding.ActivitySplashBinding
import com.btpj.wanandroid.ui.main.MainActivity
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener

/**
 * 启动页
 *
 * @author LTP 2022/4/20
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity :
    BaseActivity<SplashViewModel, ActivitySplashBinding>(R.layout.activity_splash) {

    private val mBannerList =
        listOf(R.drawable.ic_splash, R.drawable.ic_splash2, R.drawable.ic_splash3)

    private val mAdapter by lazy { SplashBannerAdapter(mBannerList) }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.apply {
            if (mViewModel.isFirstUse.get()) {
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
                val ofFloat = PropertyValuesHolder.ofFloat("alpha", 0f, 1f)
                val ofFloat1 = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f)
                val ofFloat2 = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f)
                ObjectAnimator.ofPropertyValuesHolder(ivIcon, ofFloat, ofFloat1, ofFloat2).apply {
                    duration = 2000
                    interpolator = LinearInterpolator() // 匀速
                    addListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(p0: Animator?) {}

                        override fun onAnimationEnd(p0: Animator?) {
                            MainActivity.launch(this@SplashActivity)
                            finish()
                        }

                        override fun onAnimationCancel(p0: Animator?) {}

                        override fun onAnimationRepeat(p0: Animator?) {}
                    })
                    start()
                }
            }
        }
    }
}