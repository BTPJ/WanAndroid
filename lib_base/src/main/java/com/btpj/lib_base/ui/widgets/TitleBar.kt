package com.btpj.lib_base.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.btpj.lib_base.utils.LogUtil

/**
 * 通用的顶部拦
 * @author LTP  2023/12/12
 */
@Composable
fun TitleBar(
    title: String = "",
    menu: (@Composable RowScope.() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBackClick != null) {
            Icon(
                Icons.Default.ArrowBack,
                modifier = Modifier
                    .width(50.dp)
                    .fillMaxHeight()
                    .clickable { onBackClick.invoke() }
                    .padding(vertical = 15.dp),
                contentDescription = "back",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Box(modifier = Modifier.width(50.dp))
        }
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.Center,
            softWrap = false,
            overflow = TextOverflow.Ellipsis
        )
        menu?.let {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = it
            )
        } ?: Box(modifier = Modifier.width(50.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
fun TitleBarPreview() {
    Column {
        TitleBar(
            title = "标题",
//            menu = {
//                Text(text = "菜单")
//                Text(text = "菜单2")
//            }
        ) {
            LogUtil.d("点击返回")
        }
    }
}