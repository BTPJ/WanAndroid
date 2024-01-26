package com.btpj.wanandroid.ui.web

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import android.webkit.WebView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.App
import com.btpj.wanandroid.data.bean.CollectData
import com.btpj.wanandroid.data.local.UserManager
import com.btpj.wanandroid.ui.theme.MyColor
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import kotlinx.parcelize.Parcelize

/**
 * WebView
 *
 * @author LTP 2023/12/14
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebPage(
    webViewModel: WebViewModel = hiltViewModel(),
    webType: WebType,
    collectedFlag: String? = null, // null表示未知，"0"表示未收藏，"1"表示已收藏
    navHostController: NavHostController
) {
    var pageTitle by remember { mutableStateOf("加载中...") }
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var progress by remember { mutableFloatStateOf(0.1f) }
    val webViewState = rememberWebViewState(url = webType.link)
    val webViewNavigator = rememberWebViewNavigator()
    var collected by remember { mutableStateOf(collectedFlag == "1") }
    val collectUrlList by webViewModel.collectUrlList.collectAsState()

    LaunchedEffect(webType.link) {
        if (collectedFlag == null && UserManager.isLogin()) {
            webViewModel.fetchCollectUrlList()
        }
    }

    Column {
        TitleBar(title = pageTitle, menu = {
            if (collectedFlag == null) {
                // 遍历用户收藏的网址，手动设置为已收藏
                collectUrlList.find { it.link == webType.link }?.let {
                    collected = true
                }
            }

            Icon(imageVector = if (collected) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Collect",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.clickable {
                    when (webType) {
                        is WebType.OnSiteArticle -> {
                            if (collected) {
                                webViewModel.unCollectArticle(webType.articleId) {
                                    collected = false
                                    App.appViewModel.emitCollectEvent(
                                        CollectData(
                                            webType.articleId,
                                            webType.link,
                                            false
                                        )
                                    )
                                }
                            } else {
                                webViewModel.collectArticle(webType.articleId) {
                                    collected = true
                                    App.appViewModel.emitCollectEvent(
                                        CollectData(
                                            webType.articleId,
                                            webType.link,
                                            true
                                        )
                                    )
                                }
                            }
                        }

                        is WebType.OutSiteArticle -> {
                            if (collected) {
                                webViewModel.unCollectArticle(webType.articleId) {
                                    collected = false
                                }
                            } else {
                                webViewModel.collectOutSiteArticle(
                                    webType.title, webType.author, webType.link
                                ) { collected = true }
                            }
                        }

                        is WebType.Url -> {
                            if (collected) {
                                webType.id?.let {
                                    webViewModel.unCollectUrl(it) {
                                        collected = false
                                    }
                                }
                            } else {
                                webViewModel.collectUrl(webType.name, webType.link) {
                                    collected = true
                                }
                            }
                        }
                    }
                })
            Icon(imageVector = Icons.Default.MoreVert,
                contentDescription = "MoreVert",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable { expanded = true })

            DropdownMenu(expanded = expanded,
                offset = DpOffset(0.dp, 16.dp),
                onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text = { Text(text = "分享") }, onClick = {
                    context.startActivity(Intent.createChooser(Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "${pageTitle}:${webType.link}")
                        type = "text/plain"
                    }, "分享到"))
                    expanded = false
                })
                DropdownMenuItem(text = { Text(text = "刷新") }, onClick = {
                    webViewNavigator.reload()
                    expanded = false
                })
                DropdownMenuItem(text = { Text(text = "用浏览器打开") }, onClick = {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(webType.link)))
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
            })
    }
}

@Parcelize
sealed class WebType(open var link: String) : Parcelable {
    data class OnSiteArticle(val articleId: Int, override var link: String) : WebType(link)
    data class OutSiteArticle(
        val articleId: Int, val title: String, val author: String, override var link: String
    ) : WebType(link)

    data class Url(val id: Int? = null, val name: String, override var link: String) : WebType(link)
}