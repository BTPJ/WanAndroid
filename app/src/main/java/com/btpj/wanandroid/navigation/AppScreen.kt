package com.btpj.wanandroid.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.btpj.wanandroid.ui.main.MainPage
import com.btpj.wanandroid.ui.splash.SplashPage
import com.btpj.wanandroid.ui.splash.SplashViewModel
import com.btpj.wanandroid.ui.theme.WanAndroidTheme

@Composable
fun AppScreen(
    navHostController: NavHostController,
    splashViewModel: SplashViewModel = viewModel()
) {
    val showSplash by splashViewModel.isFirstUse.collectAsState()

    WanAndroidTheme(isStatusBarTransparent = showSplash) {
        if (showSplash) {
            // 显示闪屏页
            SplashPage {
                splashViewModel.emitFirstUse(false)
            }
        } else {
            MainPage(navHostController = navHostController)
        }
    }
}