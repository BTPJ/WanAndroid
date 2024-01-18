package com.btpj.wanandroid.ui.main.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.btpj.lib_base.ext.navigate
import com.btpj.lib_base.ui.widgets.CoilImage
import com.btpj.wanandroid.App
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.local.UserManager
import com.btpj.wanandroid.navigation.Route
import com.btpj.wanandroid.ui.theme.MyColor
import com.btpj.wanandroid.ui.web.WebType

/**
 * 我的Tab
 * @author LTP  2023/12/14
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MinePage(
    mineViewModel: MineViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val uiState by mineViewModel.uiState.collectAsState()
    val user by App.appViewModel.user.collectAsState()

    fun onRefresh() {
        mineViewModel.fetchPoints()
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.showLoading,
        onRefresh = { onRefresh() }
    )

    LaunchedEffect(key1 = Unit, block = {
        if (uiState.data == null || user == null) {
            onRefresh()
        }
    })

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            // pullRefresh必须配合LazyColumn
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn {
            item {
                Column {
                    Row(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.primary)
                            .fillMaxWidth()
                            .height(150.dp)
                            .clickable(enabled = !UserManager.isLogin()) {
                                navHostController.navigate(Route.LOGIN)
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CoilImage(
                            model = R.drawable.ic_user_round,
                            modifier = Modifier.size(72.dp)
                        )
                        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                            Text(
                                text = user?.nickname ?: "请登录",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                modifier = Modifier.padding(top = 10.dp),
                                text = "id: ${uiState.data?.userId ?: "-"}   排名: ${uiState.data?.rank ?: "-"}",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 14.sp
                            )
                        }
                    }

                    ListItemWithIcon(icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_points),
                            contentDescription = "Points",
                            tint = MyColor.Blue_4cd2f5
                        )
                    },
                        title = stringResource(id = R.string.integral_rank),
                        value = {
                            Text(
                                text = stringResource(id = R.string.my_integral),
                                color = LocalContentColor.current.copy(0.5f),
                                fontSize = 14.sp
                            )
                            Text(
                                text = uiState.data?.coinCount?.toString() ?: "-",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 15.sp
                            )
                        }) { navHostController.navigate(Route.INTEGRAL_RANK) }

                    ListItemWithIcon(icon = {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            tint = MyColor.Red_FF4A57
                        )
                    }, title = stringResource(id = R.string.my_collect)) {
                        navHostController.navigate(Route.MY_COLLECT)
                    }

                    ListItemWithIcon(icon = {
                        Icon(
                            painterResource(id = R.drawable.ic_article),
                            contentDescription = "",
                            tint = MyColor.Blue_4cd2f5
                        )
                    }, title = stringResource(id = R.string.my_share_article)) {
                        navHostController.navigate(Route.SHARE_LIST)
                    }

                    Divider(
                        Modifier.height(5.dp),
                        color = LocalContentColor.current.copy(0.1f)
                    )

                    ListItemWithIcon(icon = {
                        Icon(
                            painterResource(id = R.drawable.ic_web),
                            contentDescription = "Web",
                            tint = MyColor.Blue_4cd2f5
                        )
                    }, title = stringResource(id = R.string.open_web)) {
                        navHostController.navigate(
                            Route.WEB,
                            bundleOf(
                                "webType" to WebType.Url(
                                    name = "开源网站",
                                    link = "https://www.wanandroid.com/"
                                )
                            )
                        )
                    }

                    ListItemWithIcon(icon = {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MyColor.Blue_4cd2f5
                        )
                    }, title = stringResource(id = R.string.system_settings)) {
                        navHostController.navigate(Route.SETTING)
                    }
                }
            }
        }

        user?.let {
            PullRefreshIndicator(
                refreshing = uiState.showLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun ListItemWithIcon(
    icon: @Composable () -> Unit,
    title: String,
    value: @Composable (() -> Unit)? = null,
    onItemClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onItemClick.invoke() }
            .padding(horizontal = 10.dp)
            .height(60.dp)
    ) {
        icon()
        Text(text = title, modifier = Modifier.padding(start = 10.dp))
        Spacer(modifier = Modifier.weight(1f))
        value?.invoke()
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "KeyboardArrowRight",
            tint = LocalContentColor.current.copy(alpha = 0.5f),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ListItemWithIconPreview() {
    ListItemWithIcon(icon = {
        Icon(painter = painterResource(id = R.drawable.ic_points), contentDescription = "")
    }, title = "积分排行") {
    }
}