package com.btpj.lib_base.ui.widgets

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.btpj.lib_base.utils.LogUtil

/**
 * 通用的顶部拦
 * @author LTP  2023/12/12
 */
@Composable
fun TitleBar(
    title: String,
    showBackBtn: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    menu: String? = null,
    onMenuClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.ArrowBack,
            modifier = Modifier
                .width(80.dp)
                .fillMaxHeight()
                .padding(end = 20.dp)
                .clickable { onBackClick?.invoke() }
                .padding(vertical = 15.dp),
            contentDescription = "back",
            tint = Color.White
        )
        Text(
            text = title,
            color = Color.White,
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.Center,
            softWrap = false,
            overflow = TextOverflow.Ellipsis
        )
        Box(modifier = Modifier
            .width(80.dp)
            .fillMaxHeight()
            .padding(start = 20.dp)
            .clickable(enabled = menu != null) {
                onMenuClick?.invoke()
            }
            .padding(end = 20.dp),
            contentAlignment = Alignment.CenterEnd) {
            Text(
                text = menu ?: "",
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TitleBarPreview() {
    Column {
        TitleBar(
            title = "标题", true,
            onBackClick = { LogUtil.d("点击返回") },
            menu = "菜单"
        ) {
            LogUtil.d("点击菜单")
        }
    }
}
