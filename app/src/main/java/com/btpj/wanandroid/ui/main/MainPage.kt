package com.btpj.wanandroid.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        NavGraph(navController, it)
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
    // 不喜欢material3的NavigationBar效果，故使用的是material的BottomNavigation
    // 注意内部的Text等组件也得搭配material的，不然效果出不来
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        navBarItems.forEach { item ->
            BottomNavigationItem(
                selected = navDestination?.hierarchy?.any { it.route == item.route } == true,
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.route,
                        modifier = Modifier
                            .size(22.dp)
                            .padding(bottom = 4.dp)
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                unselectedContentColor = MaterialTheme.colorScheme.onPrimary.copy(0.3f),
                label = {
                    Text(
                        text = stringResource(id = item.label),
                        fontSize = 12.sp
                    )
                },
                onClick = {
                    navController.navigate(item.route) {
                        // 这里让多个Tab下返回时，不是回到首页，而是直接退出
                        navDestination?.id?.let {
                            popUpTo(it) {
                                // 跳转时保存页面状态
                                saveState = true
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