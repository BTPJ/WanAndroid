@file:JvmName("WebActivityKt")

package com.btpj.wanandroid.ui.web

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.ui.theme.WanAndroidTheme
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

class WebActivity2 : ComponentActivity() {

    companion object {
        private const val EXTRA_ARTICLE = "extra_article"

        /**
         * 页面跳转
         *
         * @param context Context
         * @param article Article
         */
        fun launch(context: Context, url: String) {
            context.startActivity(Intent(context, WebActivity2::class.java).apply {
                putExtra(EXTRA_ARTICLE, url)
            })
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
                    intent.getStringExtra(EXTRA_ARTICLE)?.let {
                        WebPage(url = it) { finish() }
                    }
                }
            }
        }
    }
}

@Composable
fun WebPage(url: String, onBackClick: (() -> Unit)? = null) {
    var pageTitle by remember { mutableStateOf(url) }
    Column {
        TitleBar(title = pageTitle, onBackClick = onBackClick)

        val webViewState = rememberWebViewState(url = url)
        WebView(state = webViewState,
            onCreated = {
                it.settings.run { javaScriptEnabled = true }
            },
            client = object : AccompanistWebViewClient() {},
            chromeClient = object : AccompanistWebChromeClient() {
                override fun onReceivedTitle(view: WebView, title: String?) {
                    super.onReceivedTitle(view, title)
                    pageTitle = title ?: ""
                }
            }
        )

    }
}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview2() {
    WanAndroidTheme {
        WebPage("Android")
    }
}