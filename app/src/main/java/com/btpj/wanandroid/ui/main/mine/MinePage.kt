package com.btpj.wanandroid.ui.main.mine

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.btpj.wanandroid.R

/**
 * 我的Tab
 * @author LTP  2023/12/14
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MinePage(mineViewModel: MineViewModel = viewModel()) {
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
        Row(modifier = Modifier.background(color = MaterialTheme.colors.primary)) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "userIcon")
            Column {
                Text(text = uiState?.data?.nickname ?: "")
                Text(text = "id: ${integral?.userId ?: "-"} 排名: ${integral?.rank ?: "-"}")
            }
        }

        Column {

        }

    }
}

@Composable
fun ListItemWithIcon(
    icon: @Composable () -> Unit,
    title: String,
    value: @Composable (() -> Unit)? = null
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        icon()
        Text(text = title)
        Spacer(modifier = Modifier.weight(1f))
        value?.invoke()
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "KeyboardArrowRight"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListItemWithIconPreview() {
    ListItemWithIcon(icon = {
        Icon(painter = painterResource(id = R.drawable.ic_points), contentDescription = "")
    }, title = "积分排行") {
        Text(text = "我的积分: ")
        Text(text = "100000")
    }
}