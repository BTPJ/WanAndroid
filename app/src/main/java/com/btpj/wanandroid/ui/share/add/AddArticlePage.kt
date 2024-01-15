package com.btpj.wanandroid.ui.share.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.btpj.lib_base.ui.widgets.LoadingDialog
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.App
import com.btpj.wanandroid.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author LTP  2024/1/4
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AddArticlePage(
    navHostController: NavHostController,
    addArticleViewModel: AddArticleViewModel = viewModel()
) {

    var title by rememberSaveable { mutableStateOf("") }
    var link by rememberSaveable { mutableStateOf("") }
    var showLoadingDialog by remember { mutableStateOf(false) }

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    if (showLoadingDialog) {
        LoadingDialog(loadingText = "提交中...") { showLoadingDialog = false }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TitleBar(title = stringResource(id = R.string.share_article), menu = {
            Icon(
                painter = painterResource(id = R.drawable.ic_integral_rule),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                }
            )
        }) { navHostController.popBackStack() }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 20.dp, end = 20.dp),
            value = title,
            label = { Text(text = stringResource(id = R.string.article_title)) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                    0.4f
                )
            ),
            onValueChange = { title = it })
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 30.dp),
            value = link,
            label = { Text(text = stringResource(id = R.string.article_link)) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                    0.4f
                )
            ),
            onValueChange = { link = it })
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 20.dp, end = 20.dp),
            enabled = title.trim().isNotEmpty() && link.trim().isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.6f),
                disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(0.8f)
            ),
            onClick = {
                // 执行提交操作
                showLoadingDialog = true
                addArticleViewModel.addArticle(
                    title,
                    link,
                    errorCallback = { showLoadingDialog = false }) {
                    // 分享成功后的操作
                    showLoadingDialog = false
                    App.appViewModel.emitShareArticleSuccess()
                    navHostController.popBackStack()
                }
            }) {
            Text(text = stringResource(id = R.string.share))
        }
    }

    BottomSheetTip(bottomSheetState = bottomSheetState)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetTip(bottomSheetState: ModalBottomSheetState) {
    ModalBottomSheetLayout(
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetState = bottomSheetState,
        sheetContent = {
            // 底部弹窗的内容
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "温馨提示",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = "1. 只要是任何好文都可以分享哈，并不一定要是原创！投递的文章会进入广场 tab;\n " +
                            "2. CSDN，掘金，简书等官方博客站点会直接通过，不需要审核;\n " +
                            "3. 其他个人站点会进入审核阶段，不要投递任何无效链接，测试的请尽快删除，否则可能会对你的账号产生一定影响;\n " +
                            "4. 目前处于测试阶段，如果你发现500等错误，可以向我提交日志，让我们一起使网站变得更好。\n " +
                            "5. 由于本站只有我一个人开发与维护，会尽力保证24小时内审核，当然有可能哪天太累，会延期，请保持佛系...",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                )
            }
        }) {}
}