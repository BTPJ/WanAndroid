package com.btpj.wanandroid.ui.collect.url

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.btpj.lib_base.ext.toHtml
import com.btpj.wanandroid.data.bean.CollectUrl
import com.btpj.wanandroid.ui.main.RefreshList
import com.btpj.wanandroid.ui.theme.MyColor

/**
 * @author LTP  2023/12/25
 */
@Composable
fun CollectUrlPage(
    collectUrlViewModel: CollectUrlViewModel = viewModel(),
    lazyListState: LazyListState,
    onUrlClick: (CollectUrl) -> Unit
) {
    RefreshList(
        viewModel = collectUrlViewModel,
        lazyListState = lazyListState,
        onRefresh = { collectUrlViewModel.fetchCollectUrlList() },
        itemContent = { collectUrl ->
            UrlItem(collectUrl, onUnCollectClick = {
                collectUrlViewModel.unCollectUrl(collectUrl.id)
            }, onUrlClick)
        })
}

@Composable
fun UrlItem(
    collectUrl: CollectUrl,
    onUnCollectClick: (CollectUrl) -> Unit = {},
    onUrlClick: (CollectUrl) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable { onUrlClick.invoke(collectUrl) },
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = collectUrl.name.toHtml().toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = LocalContentColor.current.copy(alpha = 0.9f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = collectUrl.link.toHtml().toString(),
                    fontSize = 13.sp,
                    color = LocalContentColor.current.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = MyColor.Red_FF4A57,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clickable {
                            onUnCollectClick.invoke(collectUrl)
                        }
                )
            }
        }
    }
}