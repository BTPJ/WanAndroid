package com.btpj.wanandroid.ui.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.R

/**
 * 首页Tab
 * @author LTP  2023/12/14
 */
@Composable
fun HomePage(navHostController: NavHostController) {
    Column {
        TitleBar(title = stringResource(id = R.string.tab_home), showBackBtn = false)
    }
}