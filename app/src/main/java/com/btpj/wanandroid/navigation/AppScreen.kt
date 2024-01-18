package com.btpj.wanandroid.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.btpj.wanandroid.ui.main.MainPage
import com.btpj.wanandroid.ui.splash.SplashPage
import com.btpj.wanandroid.ui.theme.WanAndroidTheme

@Composable
fun AppScreen(
    navHostController: NavHostController,
    appScreenViewModel: AppScreenViewModel = hiltViewModel()
) {
    val showSplash by appScreenViewModel.isFirstUse.collectAsState()

    WanAndroidTheme(isStatusBarTransparent = showSplash) {
        if (showSplash) {
            // 显示闪屏页
            SplashPage {
                appScreenViewModel.emitFirstUse(false)
            }
        } else {
            MainPage(navHostController = navHostController)
        }
    }
}