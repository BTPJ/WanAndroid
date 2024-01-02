package com.btpj.wanandroid.ui.main.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.btpj.lib_base.ui.widgets.Banner
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.ui.main.ArticleItem
import com.btpj.wanandroid.ui.main.ArticleRefreshList

/**
 * 首页Tab
 * @author LTP  2023/12/14
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomePage(
    homeViewModel: HomeViewModel = viewModel(),
    onArticleClick: (Article) -> Unit
) {
    val banners by homeViewModel.bannerList.collectAsState()

    Column {
        TitleBar(title = stringResource(id = R.string.tab_home), showBackBtn = false)

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
                        images = banners.map { it.imagePath }) {}
                }
            }) {
            ArticleItem(article = it, onArticleClick = onArticleClick)
        }
    }
}
