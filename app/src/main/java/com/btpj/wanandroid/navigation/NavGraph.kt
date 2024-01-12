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
import com.btpj.wanandroid.ui.collect.CollectPage
import com.btpj.wanandroid.ui.integral.rank.IntegralRankPage
import com.btpj.wanandroid.ui.integral.record.IntegralRecordPage
import com.btpj.wanandroid.ui.login.LoginPage
import com.btpj.wanandroid.ui.login.register.RegisterPage
import com.btpj.wanandroid.ui.main.home.HomePage
import com.btpj.wanandroid.ui.main.mine.MinePage
import com.btpj.wanandroid.ui.main.project.ProjectPage
import com.btpj.wanandroid.ui.main.wechat.WechatPage
import com.btpj.wanandroid.ui.main.square.SquarePage
import com.btpj.wanandroid.ui.setting.SettingPage
import com.btpj.wanandroid.ui.share.add.AddArticlePage
import com.btpj.wanandroid.ui.share.list.MyArticlePage
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
            SquarePage(navHostController) {
                navHostController.navigate(
                    Route.WEB,
                    bundleOf("url" to it.link)
                )
            }
        }
        composable(Route.WECHAT) {
            WechatPage {
                navHostController.navigate(
                    Route.WEB,
                    bundleOf("url" to it.link)
                )
            }
        }
        composable(Route.MINE) {
            MinePage(navHostController = navHostController)
        }
        composable(Route.LOGIN) {
            LoginPage(navHostController = navHostController)
        }
        composable(Route.REGISTER) {
            RegisterPage(navHostController = navHostController)
        }
        composable(Route.MY_COLLECT) {
            CollectPage(navHostController = navHostController, onCollectUrlClick = {
                navHostController.navigate(
                    Route.WEB,
                    bundleOf("url" to it.link)
                )
            }) {
                navHostController.navigate(
                    Route.WEB,
                    bundleOf("url" to it.link)
                )
            }
        }
        composable(Route.SETTING) {
            SettingPage(navHostController = navHostController)
        }
        composable(Route.INTEGRAL_RANK) {
            IntegralRankPage(navHostController = navHostController)
        }
        composable(Route.INTEGRAL_RANK_RECORD) {
            IntegralRecordPage(navHostController = navHostController)
        }
        composable(Route.SHARE_LIST) {
            MyArticlePage(navHostController = navHostController) {
                navHostController.navigate(
                    Route.WEB,
                    bundleOf("url" to it.link)
                )
            }
        }
        composable(Route.ADD_ARTICLE) {
            AddArticlePage(navHostController = navHostController)
        }
        composable(Route.WEB) {
            it.arguments?.getString("url")
                ?.let { url ->
                    WebPage(url, navHostController = navHostController) {

                    }
                }
        }
    }
}

object Route {
    const val HOME = "home"
    const val PROJECT = "project"
    const val SQUARE = "square"
    const val WECHAT = "wechat"
    const val MINE = "mine"
    const val MY_COLLECT = "myCollect"
    const val SETTING = "setting"
    const val WEB = "web"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val SHARE_LIST = "share_list"
    const val ADD_ARTICLE = "add_article"
    const val INTEGRAL_RANK = "integral_rank"
    const val INTEGRAL_RANK_RECORD = "integral_rank_record"
}

