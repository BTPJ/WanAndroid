package com.btpj.wanandroid.navigation

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.btpj.wanandroid.ext.decorFitsSystemWindows
import com.btpj.wanandroid.ui.main.MainPage
import com.btpj.wanandroid.ui.splash.SplashPage
import com.btpj.wanandroid.ui.theme.WanAndroidTheme

@Composable
fun AppScreen(
    navHostController: NavHostController,
    appScreenViewModel: AppScreenViewModel = hiltViewModel()
) {
    val window = (LocalContext.current as? Activity)?.window
    val showSplash by appScreenViewModel.isFirstUse.collectAsState()

    WanAndroidTheme(isStatusBarTransparent = showSplash) {
        if (showSplash) {
            // 沉浸式状态栏
            window?.decorFitsSystemWindows(false)
            // 显示闪屏页
            SplashPage {
                appScreenViewModel.emitFirstUse(false)
            }
        } else {
            // 取消沉浸式状态栏
            window?.decorFitsSystemWindows(true)
            MainPage(navHostController = navHostController)
        }
    }
}


