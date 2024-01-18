package com.btpj.wanandroid.ui.main.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.btpj.lib_base.ui.widgets.Banner
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.data.bean.Banner
import com.btpj.wanandroid.ui.main.ArticleItem
import com.btpj.wanandroid.ui.main.ArticleRefreshList

/**
 * 首页Tab
 * @author LTP  2023/12/14
 */
@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomePage(
    // 其实一个DataRepository，也没必要引入hilt
    homeViewModel: HomeViewModel = hiltViewModel(),
    onSearch: () -> Unit,
    onBannerClick: (Banner) -> Unit = {},
    onArticleClick: (Article) -> Unit
) {
    val banners by homeViewModel.bannerList.collectAsState()

    Column {
        TitleBar(title = stringResource(id = R.string.tab_home), menu = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onSearch() }
            )
        })

        ArticleRefreshList(
            viewModel = homeViewModel,
            lazyListState = rememberLazyListState(),
            onRefresh = {
                homeViewModel.fetchBanners()
                homeViewModel.fetchHomeArticlePageList()
            },
            onLoadMore = { homeViewModel.fetchHomeArticlePageList(false) },
            headerContent = {
                // 轮播图
                if (banners.isNotEmpty()) {
                    Banner(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        images = banners.map { it.imagePath }) {
                        onBannerClick(banners[it])
                    }
                }
            }) {
            ArticleItem(
                article = it,
                homeViewModel,
                onArticleClick = onArticleClick
            )
        }
    }
}
