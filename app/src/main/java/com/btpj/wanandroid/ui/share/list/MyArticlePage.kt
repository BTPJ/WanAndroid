package com.btpj.wanandroid.ui.share.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.btpj.lib_base.ext.toHtml
import com.btpj.lib_base.ui.widgets.CusAlertDialog
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.CollectUrl
import com.btpj.wanandroid.ui.main.RefreshList
import com.btpj.wanandroid.ui.theme.MyColor

/**
 * @author LTP  2023/12/25
 */
@Composable
fun MyArticlePage(
    navHostController: NavHostController,
    myArticleViewModel: MyArticleViewModel = viewModel(),
    onArticleClick: (Article) -> Unit
) {
    Column {
        TitleBar(title = "我分享的文章") { navHostController.popBackStack() }
        RefreshList(
            viewModel = myArticleViewModel,
            onRefresh = { myArticleViewModel.fetchMyShareArticlePageList() },
            itemContent = { article ->
                ShareArticleItem(
                    article, onDeleteClick = {
                        myArticleViewModel.deleteMyShareArticle(it.id)
                    }, onArticleClick
                )
            })
    }
}

@Composable
fun ShareArticleItem(
    article: Article,
    onDeleteClick: (Article) -> Unit,
    onArticleClick: (Article) -> Unit = {}
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable { onArticleClick.invoke(article) },
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = article.title.toHtml().toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = LocalContentColor.current.copy(alpha = 0.9f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Text(
                    text = article.niceDate,
                    fontSize = 13.sp,
                    color = LocalContentColor.current.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Favorite",
                tint = MyColor.Gray_d8d8d8,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable { showDeleteDialog = true }
            )
        }
    }

    if (showDeleteDialog) {
        CusAlertDialog(content = { Text(text = "确定删除该文章吗?") },
            confirmText = "清理",
            onConfirm = { onDeleteClick.invoke(article) }) {
            showDeleteDialog = false
        }
    }
}