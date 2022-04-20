package com.btpj.wanandroid.ui.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.btpj.wanandroid.R
import com.btpj.wanandroid.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 启动页
 *
 * @author LTP 2022/4/20
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var mKeepSplashScreen = AtomicBoolean(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { mKeepSplashScreen.get() }
            setOnExitAnimationListener {
                MainActivity.launch(this@SplashActivity)
                finish()
            }
        }

        // 使用SplashScreen后下面也可以不要
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            delay(2000)
            mKeepSplashScreen.compareAndSet(true, false)
        }
    }
}