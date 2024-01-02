package com.btpj.wanandroid.ui.main.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.btpj.wanandroid.R
import com.btpj.wanandroid.ext.navigate
import com.btpj.wanandroid.navigation.Route
import com.btpj.wanandroid.ui.theme.MyColor

/**
 * 我的Tab
 * @author LTP  2023/12/14
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MinePage(mineViewModel: MineViewModel = viewModel(), navHostController: NavHostController) {
    val uiState by mineViewModel.uiState.observeAsState()
    val integral by mineViewModel.integral.observeAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState?.showLoading ?: false,
        onRefresh = { })

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .pullRefresh(pullRefreshState)
    ) {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .height(150.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .height(72.dp)
                    .width(72.dp)
                    .padding(start = 16.dp),
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "userIcon",
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = uiState?.data?.nickname ?: "-",
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "id: ${integral?.userId ?: "-"} 排名: ${integral?.rank ?: "-"}",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
        ) {
            ListItemWithIcon(icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_points),
                    contentDescription = "Points",
                    tint = MyColor.Blue_4cd2f5
                )
            },
                title = stringResource(id = R.string.integral_rank),
                value = {
                    Text(text = stringResource(id = R.string.my_integral))
                    Text(text = integral?.coinCount?.toString() ?: "-")
                }) {}

            ListItemWithIcon(icon = {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = MyColor.Red_FF4A57
                )
            }, title = stringResource(id = R.string.my_collect)) {}

            ListItemWithIcon(icon = {
                Icon(
                    painterResource(id = R.drawable.ic_article),
                    contentDescription = "",
                    tint = MyColor.Blue_4cd2f5
                )
            }, title = stringResource(id = R.string.my_share_article)) {}

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
                    bundleOf("url" to "https://www.wanandroid.com/")
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