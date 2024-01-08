package com.btpj.wanandroid.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.btpj.lib_base.http.RetrofitManager
import com.btpj.lib_base.ui.widgets.CusAlertDialog
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.App
import com.btpj.wanandroid.data.local.UserManager
import com.btpj.wanandroid.ext.navigate
import com.btpj.wanandroid.navigation.Route

/**
 * 设置
 *
 * @author LTP 2023/12/12
 */
@Composable
fun SettingPage(
    navHostController: NavHostController, viewModel: SettingViewModel = viewModel()
) {
    val showLogoutBtn by viewModel.showLogoutBtn.observeAsState()
    val haveNewVersion by viewModel.haveNewVersion.observeAsState()
    val cacheSize by viewModel.cacheSize.observeAsState()
    val appVersionName by viewModel.appVersionName.observeAsState()

    var showClearCacheDialog by remember { mutableStateOf(false) }
    var showIntroDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.start()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TitleBar(title = "设置") { navHostController.popBackStack() }
        ListItem(label = "清除缓存", value = cacheSize) {
            showClearCacheDialog = true
        }
        ListItem(
            label = "版本", value = appVersionName, showRedCircle = haveNewVersion
        ) {
            viewModel.checkAppUpdate(true)
        }
        ListItem(label = "作者", value = "BTPJ") {
            showIntroDialog = true
        }
        ListItem(label = "项目源码") {
            navHostController.navigate(
                Route.WEB, bundleOf("url" to "https://gitee.com/BTPJ_git/WanAndroid")
            )
        }
        if (showLogoutBtn == true) {
            Button(modifier = Modifier
                .padding(top = 50.dp)
                .width(200.dp),
                shape = RoundedCornerShape(4.dp),
                onClick = { showLogoutDialog = true }) {
                Text(text = "退出登录")
            }
        }

        if (showClearCacheDialog) {
            CusAlertDialog(content = { Text(text = "确定清理缓存吗") },
                confirmText = "清理",
                onConfirm = { viewModel.clearAllCache() }) {
                showClearCacheDialog = false
            }
        }

        if (showIntroDialog) {
            CusAlertDialog(
                titleText = "联系作者",
                content = { Text("Q\tQ：1069113473\n\n微信：BTPJ1314\n\n邮箱：1069113473@qq.com") },
                showCancel = false
            ) { showIntroDialog = false }
        }

        if (showLogoutDialog) {
            CusAlertDialog(content = { Text("确定退出登录吗?") }, onConfirm = {
                // 手动清除cookie
                RetrofitManager.cookieJar.clear()
                UserManager.logout()
                App.appViewModel.emitUser(null)
                navHostController.popBackStack()
            }) { showLogoutDialog = false }
        }
    }
}

@Composable
fun ListItem(
    label: String,
    value: String? = null,
    showBottomLine: Boolean = true,
    showRedCircle: Boolean? = false,
    onItemClick: (() -> Unit)? = null
) {

    Column(Modifier.clickable(enabled = onItemClick != null) { onItemClick?.invoke() }) {
        Row(
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label)
            Spacer(modifier = Modifier.weight(1f))
            if (value != null) {
                Text(text = value)
            }
            if (showRedCircle == true) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color = Color.Red)
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "ArrowRight",
                tint = LocalContentColor.current.copy(alpha = 0.5f),
                modifier = Modifier.size(30.dp)
            )
        }
        if (showBottomLine) {
            Divider(modifier = Modifier.height(0.2.dp))
        }
    }
}