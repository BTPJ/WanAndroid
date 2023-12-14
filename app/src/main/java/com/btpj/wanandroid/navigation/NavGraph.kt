package com.btpj.wanandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.btpj.wanandroid.ui.main.NavBarItem
import com.btpj.wanandroid.ui.main.home.HomePage
import com.btpj.wanandroid.ui.main.mine.MinePage
import com.btpj.wanandroid.ui.main.project.ProjectPage
import com.btpj.wanandroid.ui.main.square.SquarePage
import com.btpj.wanandroid.ui.main.wechat.WechatPage
import com.btpj.wanandroid.ui.setting.SettingPage
import com.btpj.wanandroid.ui.web.WebPage

/**
 * @author LTP  2023/12/14
 */
@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = NavBarItem.Home.route
    ) {
        composable(Route.HOME) {
            HomePage(navHostController = navHostController)
        }
        composable(Route.PROJECT) {
            ProjectPage(navHostController = navHostController)
        }
        composable(Route.SQUARE) {
            SquarePage(navHostController = navHostController)
        }
        composable(Route.WECHAT) {
            WechatPage(navHostController = navHostController)
        }
        composable(Route.MINE) {
            MinePage(navHostController = navHostController)
        }
        composable(Route.SETTING) {
            SettingPage(navHostController = navHostController)
        }
        composable(Route.WEB) {
            val url = it.arguments?.getString("url")
            url?.let { url -> WebPage(url, navHostController = navHostController) {} }
        }
    }
}

object Route {
    const val HOME = "home"
    const val PROJECT = "project"
    const val SQUARE = "square"
    const val WECHAT = "wechat"
    const val MINE = "mine"
    const val SETTING = "setting"
    const val WEB = "web"
}

