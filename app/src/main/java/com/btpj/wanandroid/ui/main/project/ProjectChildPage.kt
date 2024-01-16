package com.btpj.wanandroid.ui.main.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.btpj.lib_base.ext.toHtml
import com.btpj.lib_base.ui.widgets.RefreshList
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.ui.main.ArticleRefreshList
import com.btpj.wanandroid.ui.main.ArticleViewModel
import com.btpj.wanandroid.ui.theme.MyColor

/**
 * @author LTP  2023/12/21
 */
@Composable
fun ProjectChildPage(
    categoryId: Int,
    lazyListState: LazyListState,
    // 因为categoryId是动态的，所以需要添加key来确保每个categoryId对应一个ViewModel
    projectChildViewModel: ProjectChildViewModel = viewModel(key = "$categoryId"),
    onArticleClick: (Article) -> Unit
) {
    ArticleRefreshList(
        viewModel = projectChildViewModel,
        lazyListState = lazyListState,
        onRefresh = {
            if (categoryId == -1) {
                projectChildViewModel.fetchNewProjectPageList()
            } else {
                projectChildViewModel.fetchProjectPageList(categoryId)
            }
        },
        onLoadMore = {
            if (categoryId == -1) {
                projectChildViewModel.fetchNewProjectPageList(false)
            } else {
                projectChildViewModel.fetchProjectPageList(categoryId, false)
            }
        }) {
        ProjectArticleItem(article = it, projectChildViewModel, onArticleClick = onArticleClick)
    }
}

@Composable
fun ProjectArticleItem(
    article: Article,
    articleViewModel: ArticleViewModel,
    onArticleClick: (Article) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable { onArticleClick.invoke(article) },
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (article.author.isEmpty()) article.shareUser.toHtml()
                        .toString() else article.author.toHtml().toString(),
                    color = LocalContentColor.current.copy(alpha = 0.6f),
                    fontSize = 13.sp
                )
                Text(
                    text = article.niceDate,
                    color = LocalContentColor.current.copy(alpha = 0.6f),
                    fontSize = 13.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                AsyncImage(
                    model = article.envelopePic,
                    contentDescription = "",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = article.title.toHtml().toString(),
                        color = LocalContentColor.current.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = article.desc.toHtml().toString(),
                        modifier = Modifier.padding(top = 12.dp),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        color = LocalContentColor.current.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${article.superChapterName}·${article.chapterName}".toHtml().toString(),
                    color = LocalContentColor.current.copy(alpha = 0.6f),
                    fontSize = 13.sp
                )
                Icon(
                    imageVector = if (article.collect) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = MyColor.Red_FF4A57,
                    modifier = Modifier.clickable {
                        articleViewModel.handleCollect(article)
                    }
                )
            }
        }
    }
}