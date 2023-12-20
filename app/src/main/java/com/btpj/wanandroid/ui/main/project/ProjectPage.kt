package com.btpj.wanandroid.ui.main.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.R

/**
 * 项目Tab
 * @author LTP  2023/12/14
 */
@Composable
fun ProjectPage(navHostController: NavHostController) {
    TitleBar(title = stringResource(id = R.string.tab_project), showBackBtn = false)
}