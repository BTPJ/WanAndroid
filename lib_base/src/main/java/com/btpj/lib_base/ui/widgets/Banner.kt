package com.btpj.lib_base.ui.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * 轮播图
 *
 * @author LTP  2024/1/2
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Banner(
    modifier: Modifier,
    images: List<String>,
    autoScroll: Boolean = true,
    autoScrollInterval: Long = 3000L,
    onBannerItemClick: ((index: Int) -> Unit)? = null
) {
    val loopingCount = Int.MAX_VALUE
    val startIndex = loopingCount / 2
    val pagerState = rememberPagerState(initialPage = startIndex) { loopingCount }

    /** 计算实际索引 */
    fun pageMapper(index: Int): Int {
        return (index - startIndex).floorMod(images.count())
    }

    val currentIndex: State<Int> = remember {
        derivedStateOf { pageMapper(pagerState.currentPage) }
    }

    LaunchedEffect(true) {
        while (autoScroll && images.isNotEmpty()) {
            delay(autoScrollInterval)
            // 手指滑动时不轮播
            if (!pagerState.isScrollInProgress) {
                pagerState.animateScrollToPage(currentIndex.value + 1)
            }
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
        HorizontalPager(state = pagerState) {
            val index = it % images.size // 取模运算获取实际的索引
            CoilImage(
                model = images[index],
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onBannerItemClick?.invoke(index) })
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            images.forEachIndexed { index, _ ->
                PagerIndicator(selected = index == currentIndex.value)
                Spacer(modifier = Modifier.width(4.dp)) // 间隔
            }
        }
    }
}

/** 计算除法后取余 */
fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}

@Composable
fun PagerIndicator(selected: Boolean = false) {
    val color = if (selected) Color.Red else Color.Gray
    val size = if (selected) 8.dp else 6.dp
    Box(
        modifier = Modifier
            .size(size)
            .background(color = color, shape = CircleShape)
    )
}