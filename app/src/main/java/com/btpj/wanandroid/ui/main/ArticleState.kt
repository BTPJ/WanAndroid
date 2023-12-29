package com.btpj.wanandroid.ui.main

import androidx.compose.foundation.lazy.LazyListState
import com.btpj.wanandroid.data.bean.Article

/**
 * @author LTP  2023/12/29
 */
data class ArticleState(
    var articleList: ArrayList<Article>,
    // 滑动的状态，其实也可以放在父页面里面保存，参考SquarePage
    var lazyListState: LazyListState = LazyListState()
)
