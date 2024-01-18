package com.btpj.wanandroid.ui.share.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.btpj.lib_base.ext.toHtml
import com.btpj.lib_base.ui.widgets.CusAlertDialog
import com.btpj.lib_base.ui.widgets.RefreshList
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.App
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.navigation.Route
import com.btpj.wanandroid.ui.theme.MyColor

/**
 * @author LTP  2023/12/25
 */
@Composable
fun MyArticlePage(
    navHostController: NavHostController,
    myArticleViewModel: MyArticleViewModel = hiltViewModel(),
    onArticleClick: (Article) -> Unit
) {
    val needRefresh by App.appViewModel.shareArticleEvent.observeAsState()

    LaunchedEffect(key1 = Unit, block = {
        if (needRefresh == true) {
            myArticleViewModel.fetchMyShareArticlePageList()
        }
    })

    Column {
        TitleBar(title = "我分享的文章", menu = {
            androidx.compose.material.Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.clickable { navHostController.navigate(Route.ADD_ARTICLE) })
        }) { navHostController.popBackStack() }
        RefreshList(
            uiState = myArticleViewModel.uiState.collectAsState().value,
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
            onConfirm = { onDeleteClick.invoke(article) }) {
            showDeleteDialog = false
        }
    }
}