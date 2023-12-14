package com.btpj.wanandroid.ui.main.wechat

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.R

/**
 * 微信Tab
 * @author LTP  2023/12/14
 */
@Composable
fun WechatPage(navHostController: NavHostController) {
    TitleBar(title = stringResource(id = R.string.tab_wechat), showBackBtn = false)
}