package com.btpj.wanandroid.ui.home

import android.text.Html
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.lib_base.utils.LogUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.Tag
import com.btpj.wanandroid.ui.theme.MyColor
import kotlinx.coroutines.delay

/**
 * 首页Tab
 * @author LTP  2023/12/14
 */
@Composable
fun HomePage(
    homeViewModel: HomeViewModel = viewModel(),
    onArticleClick: (Article) -> Unit
) {
    Column {
        TitleBar(title = stringResource(id = R.string.tab_home), showBackBtn = false)
        ArticleRefreshList(
            homeViewModel = homeViewModel,
            onRefresh = { homeViewModel.fetchArticlePageList() },
            onLoadMore = { homeViewModel.fetchArticlePageList(false) }) {
            ArticleItem(article = it, onArticleClick = onArticleClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleRefreshList(
    homeViewModel: HomeViewModel,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    itemContent: @Composable (Article) -> Unit
) {
    val articleUiState by homeViewModel.articleUiState.observeAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = articleUiState?.showLoading ?: false,
        onRefresh = onRefresh
    )
    LaunchedEffect(true) { onRefresh() }
    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            articleUiState?.list?.let {
                items(it, key = { item -> item.id }) { article ->
                    Spacer(modifier = Modifier.height(6.dp))
                    itemContent(article)
                    Spacer(modifier = Modifier.height(6.dp))
                }
                item {
                    if (articleUiState?.isLoadMore == true) {
                        LogUtil.d("LTP", articleUiState.toString())
                        LoadingView()
                        LaunchedEffect(Unit) {
                            delay(500)
                            onLoadMore()
                        }
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = articleUiState?.showLoading ?: false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun ArticleItem(article: Article, onArticleClick: ((Article) -> Unit)? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable { onArticleClick?.invoke(article) },
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = article.author.ifEmpty { article.shareUser },
                        fontSize = 13.sp,
                        color = LocalContentColor.current.copy(alpha = 0.8f)
                    )
                    if (article.type == 1) Text(
                        text = "置顶",
                        color = MyColor.Red_FF4A57,
                        fontSize = 11.sp,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .border(1.dp, MyColor.Red_FF4A57)
                            .padding(horizontal = 4.dp)
                    )
                    if (article.fresh) Text(
                        text = "新",
                        color = MyColor.Red_FF4A57,
                        fontSize = 11.sp,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .border(1.dp, MyColor.Red_FF4A57)
                            .padding(horizontal = 4.dp)
                    )
                    if (article.tags.isNotEmpty()) Text(
                        text = article.tags[0].name,
                        color = MyColor.Green_669900,
                        fontSize = 11.sp,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .border(1.dp, MyColor.Green_669900)
                            .padding(horizontal = 4.dp)
                    )
                }
                Text(
                    text = article.niceDate,
                    fontSize = 13.sp,
                    color = LocalContentColor.current.copy(alpha = 0.8f)
                )
            }
            Text(
                text = Html.fromHtml(article.title).toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = LocalContentColor.current.copy(alpha = 0.9f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 12.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = Html.fromHtml(article.superChapterName + "." + article.chapterName)
                        .toString(),
                    fontSize = 13.sp,
                    color = LocalContentColor.current.copy(alpha = 0.8f)
                )
                Icon(
                    imageVector = if (article.collect) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = MyColor.Red_FF4A57
                )
            }
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(30.dp))
    }
}

@Preview()
@Composable
fun ArticleItemPreview() {
    ArticleItem(
        Article(
            apkLink = "",
            audit = 1,
            author = "",
            canEdit = false,
            chapterId = 502,
            chapterName = "自助",
            collect = true,
            courseId = 13,
            desc = "",
            descMd = "",
            envelopePic = "",
            fresh = true,
            host = "",
            id = 27688,
            link = "https://juejin.cn/post/7309443151688892468",
            niceDate = "2023-12-10 21:38",
            niceShareDate = "2023-12-10 21:36",
            origin = "",
            prefix = "",
            projectLink = "",
            publishTime = 1702215488000,
            realSuperChapterId = 493,
            selfVisible = 0,
            shareDate = 1702215402000,
            shareUser = "鸿洋",
            superChapterId = 494,
            superChapterName = "广场Tab",
            tags = listOf(Tag("本站发布", "")),
            title = "ANR的类型分类以及关于输入事件/按键响应分发超时原因原理按键响应分发超时原因原理分析按键响应分发超时原因原理分析按键响应分发超时原因原理分析分析",
            type = 1,
            userId = 2,
            visible = 1,
            zan = 0
        )
    )
}