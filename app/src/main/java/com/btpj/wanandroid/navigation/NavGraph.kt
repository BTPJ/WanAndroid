package com.btpj.wanandroid.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.btpj.wanandroid.ext.navigate
import com.btpj.wanandroid.ui.main.home.HomePage
import com.btpj.wanandroid.ui.main.mine.MinePage
import com.btpj.wanandroid.ui.main.project.ProjectPage
import com.btpj.wanandroid.ui.main.square.SquarePage
import com.btpj.wanandroid.ui.wechat.WechatPage
import com.btpj.wanandroid.ui.setting.SettingPage
import com.btpj.wanandroid.ui.web.WebPage

/**
 * @author LTP  2023/12/14
 */
@Composable
fun NavGraph(navHostController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navHostController,
        startDestination = Route.HOME,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Route.HOME) {
            HomePage {
                navHostController.navigate(
                    Route.WEB,
                    bundleOf("url" to it.link)
                )
            }
        }
        composable(Route.PROJECT) {
            ProjectPage {
                navHostController.navigate(
                    Route.WEB,
                    bundleOf("url" to it.link)
                )
            }
        }
        composable(Route.SQUARE) {
            SquarePage {
                navHostController.navigate(
                    Route.WEB,
                    bundleOf("url" to it.link)
                )
            }
        }
        composable(Route.WECHAT) {
            WechatPage()
        }
        composable(Route.MINE) {
            MinePage(navHostController = navHostController)
        }
        composable(Route.SETTING) {
            SettingPage(navHostController = navHostController)
        }
        composable(Route.WEB) {
            val url = it.arguments?.getString("url")
            url?.let { url2 -> WebPage(url2, navHostController = navHostController) {} }
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

