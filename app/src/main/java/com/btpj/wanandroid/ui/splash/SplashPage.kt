package com.btpj.wanandroid.ui.splash

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.btpj.lib_base.ui.widgets.Banner
import com.btpj.wanandroid.R
import com.btpj.wanandroid.navigation.Route

/**
 * @author LTP  2023/12/14
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SplashPage(onFinish: () -> Unit) {
    val imageList = listOf(R.drawable.ic_splash, R.drawable.ic_splash2, R.drawable.ic_splash3)
    val pagerState = rememberPagerState { imageList.size }
    Box(contentAlignment = Alignment.BottomCenter) {
        Banner(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            pagerState = pagerState,
            images = listOf(R.drawable.ic_splash, R.drawable.ic_splash2, R.drawable.ic_splash3),
            autoScroll = false,
            indicatorSize = 20,
            indicatorModifier = Modifier.padding(bottom = 50.dp)
        )
        if (pagerState.currentPage == imageList.size - 1) {
            Button(
                modifier = Modifier.padding(bottom = 90.dp),
                onClick = { onFinish() }
            ) {
                Text(text = stringResource(id = R.string.enter_immediately))
            }
        }
    }
}