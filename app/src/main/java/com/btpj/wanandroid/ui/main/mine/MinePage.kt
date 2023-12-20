package com.btpj.wanandroid.ui.main.mine

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.R
import com.btpj.wanandroid.navigation.Route

/**
 * 我的Tab
 * @author LTP  2023/12/14
 */
@Composable
fun MinePage(navHostController: NavHostController) {
    TitleBar(title = stringResource(id = R.string.tab_mine), showBackBtn = false)
    Button(onClick = { navHostController.navigate(Route.SETTING) }) {
        Text("跳转")
    }
}