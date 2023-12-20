package com.btpj.wanandroid.ui.main.square

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.R

/**
 * 广场Tab
 * @author LTP  2023/12/14
 */
@Composable
fun SquarePage(navHostController: NavHostController) {
    TitleBar(title = stringResource(id = R.string.tab_square), showBackBtn = false)
}