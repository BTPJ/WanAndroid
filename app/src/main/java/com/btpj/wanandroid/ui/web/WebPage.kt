package com.btpj.wanandroid.ui.web

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.ui.theme.MyColor
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState

/**
 * WebView
 *
 * @author LTP 2023/12/14
 */
@Composable
fun WebPage(
    url: String,
    navHostController: NavHostController,
    onCollectClick: (() -> Unit)? = null
) {
    var pageTitle by remember { mutableStateOf(url) }
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var progress by remember { mutableStateOf(0.1f) }
    val webViewState = rememberWebViewState(url = url)
    val webViewNavigator = rememberWebViewNavigator()
    Column {
        TitleBar(title = pageTitle, menu = {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Collect",
                tint = Color.White,
                modifier = Modifier.clickable {
                    onCollectClick?.invoke()
                }
            )
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "MoreVert",
                tint = Color.White,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable { expanded = true })

            DropdownMenu(
                expanded = expanded,
                offset = DpOffset(0.dp, 16.dp),
                onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text = { Text(text = "分享") }, onClick = {
                    context.startActivity(Intent.createChooser(Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "${pageTitle}:$url")
                        type = "text/plain"
                    }, "分享到"))
                    expanded = false
                })
                DropdownMenuItem(text = { Text(text = "刷新") }, onClick = {
                    webViewNavigator.reload()
                    expanded = false
                })
                DropdownMenuItem(text = { Text(text = "用浏览器打开") }, onClick = {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    expanded = false
                })
            }
        }) { navHostController.popBackStack() }

        if (progress != 1.0f) {
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = MyColor.Green_19791D
            )
        }

        WebView(state = webViewState,
            navigator = webViewNavigator,
            onCreated = {
                it.settings.run { javaScriptEnabled = true }
            },
            client = remember { object : AccompanistWebViewClient() {} },
            chromeClient = remember {
                object : AccompanistWebChromeClient() {
                    override fun onReceivedTitle(view: WebView, title: String?) {
                        super.onReceivedTitle(view, title)
                        pageTitle = title ?: ""
                    }

                    override fun onProgressChanged(view: WebView, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        progress = newProgress / 100.0f
                    }
                }
            }
        )

    }
}