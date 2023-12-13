package com.btpj.wanandroid.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.btpj.lib_base.ext.showDialog
import com.btpj.lib_base.http.RetrofitManager
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.lib_base.utils.AppUtil
import com.btpj.lib_base.utils.CacheUtil
import com.btpj.wanandroid.base.App
import com.btpj.wanandroid.data.bean.Banner
import com.btpj.wanandroid.data.local.UserManager
import com.btpj.wanandroid.ui.theme.WanAndroidTheme
import com.btpj.wanandroid.ui.web.WebActivity
import com.btpj.wanandroid.ui.web.WebActivity2

/**
 * 设置
 *
 * @author LTP 2023/12/12
 */
class SettingActivity : ComponentActivity() {

    companion object {
        /**
         * 页面启动
         * @param context Context
         */
        fun launch(context: Context) {
            context.startActivity(Intent(context, SettingActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WanAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SettingPage(this) { finish() }
                }
            }
        }
    }
}

@Composable
fun SettingPage(
    componentActivity: ComponentActivity? = null,
    viewModel: SettingViewModel = viewModel(),
    onBackClick: (() -> Unit)? = null
) {
    val showLogoutBtn by viewModel.showLogoutBtn.observeAsState()
    val haveNewVersion by viewModel.haveNewVersion.observeAsState()
    val cacheSize by viewModel.cacheSize.observeAsState()
    val appVersionName by viewModel.appVersionName.observeAsState()

    LaunchedEffect(true) {
        viewModel.start()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TitleBar(title = "设置", onBackClick = onBackClick)
        ListItem(label = "清除缓存", value = cacheSize) {
            componentActivity?.showDialog(
                "确定清理缓存吗?",
                positiveButtonText = "清理",
                positiveAction = {
                    viewModel.clearAllCache()
                })
        }
        ListItem(
            label = "版本",
            value = appVersionName,
            showRedCircle = haveNewVersion
        ) {
            viewModel.checkAppUpdate(true)
        }
        ListItem(label = "作者", value = "BTPJ") {
            componentActivity?.showDialog(
                "Q\tQ：1069113473\n\n微信：BTPJ1314\n\n邮箱：1069113473@qq.com",
                "联系作者", negativeButtonText = ""
            )
        }
        ListItem(label = "项目源码") {
            componentActivity?.let {
                WebActivity2.launch(
                    it,
                    "https://gitee.com/BTPJ_git/WanAndroid"
                )
            }
        }
        if (showLogoutBtn == true) {
            Button(modifier = Modifier
                .padding(top = 50.dp)
                .width(200.dp),
                shape = RoundedCornerShape(4.dp),
                onClick = {
                    componentActivity?.showDialog(
                        "确定退出登录吗?",
                        positiveButtonText = "退出",
                        positiveAction = {
                            // 手动清除cookie
                            RetrofitManager.cookieJar.clear()
                            UserManager.logout()
                            App.appViewModel.userEvent.value = null
                            componentActivity.finish()
                        })
                }) {
                Text(text = "退出登录")
            }
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
            Text(text = label, color = Color.DarkGray)
            Spacer(modifier = Modifier.weight(1f))
            if (value != null) {
                Text(text = value, color = Color.DarkGray)
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
                tint = Color.Gray,
                modifier = Modifier.size(30.dp)
            )
        }
        if (showBottomLine) {
            Divider(modifier = Modifier.height(0.2.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    WanAndroidTheme {
        SettingPage()
    }
}