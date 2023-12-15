package com.btpj.wanandroid.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.btpj.wanandroid.R
import com.btpj.wanandroid.navigation.NavGraph
import com.btpj.wanandroid.navigation.Route

/**
 * 主页面
 * @author LTP  2023/12/14
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // 除去几个navBarItems其他页面不展示BottomBar
            if (destination?.hierarchy?.any {
                    navBarItems.map { navBarItem -> navBarItem.route }
                        .contains(it.route)
                } == true) {
                BottomBar(navController, destination)
            }
        }
    ) {
        NavGraph(navController)
    }
}

val navBarItems = listOf(
    NavBarItem.Home,
    NavBarItem.Project,
    NavBarItem.Square,
    NavBarItem.Wechat,
    NavBarItem.Mine
)

@Composable
fun BottomBar(navController: NavController, navDestination: NavDestination?) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        navBarItems.forEach { item ->
            NavigationBarItem(
                selected = navDestination?.hierarchy?.any { it.route == item.route } == true,
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.route
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                ),
                label = { Text(text = stringResource(id = item.label)) },
                onClick = {
                    navController.navigate(item.route) {
                        // 这里让多个Tab下返回时，不是回到首页，而是直接退出
                        navDestination?.id?.let {
                            popUpTo(it) {
                                // 跳转时保存页面状态
                                saveState = false
                                // 回退到栈顶时，栈顶页面是否也关闭
                                inclusive = true
                            }
                        }
                        // 栈顶复用，避免重复点击同一个导航按钮，回退栈中多次创建实例
                        launchSingleTop = true
                        // 回退时恢复页面状态
                        restoreState = true
                    }
                })
        }
    }
}

sealed class NavBarItem(val label: Int, val icon: Int, val route: String) {
    object Home : NavBarItem(R.string.tab_home, R.drawable.ic_tab_home, Route.HOME)
    object Project : NavBarItem(R.string.tab_project, R.drawable.ic_tab_project, Route.PROJECT)
    object Square : NavBarItem(R.string.tab_square, R.drawable.ic_tab_square, Route.SQUARE)
    object Wechat : NavBarItem(R.string.tab_wechat, R.drawable.ic_tab_wechat, Route.WECHAT)
    object Mine : NavBarItem(R.string.tab_mine, R.drawable.ic_tab_mine, Route.MINE)
}