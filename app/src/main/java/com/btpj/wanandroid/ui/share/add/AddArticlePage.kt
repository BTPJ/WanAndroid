package com.btpj.wanandroid.ui.share.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.btpj.lib_base.ui.widgets.LoadingDialog
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.lib_base.utils.ToastUtil
import com.btpj.wanandroid.App
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.local.UserManager

/**
 * @author LTP  2024/1/4
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddArticlePage(
    navHostController: NavHostController,
    addArticleViewModel: AddArticleViewModel = viewModel()
) {

    var title by rememberSaveable { mutableStateOf("") }
    var link by rememberSaveable { mutableStateOf("") }
    var showLoadingDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    if (showLoadingDialog) {
        LoadingDialog(loadingText = "提交中...") { showLoadingDialog = false }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TitleBar(title = stringResource(id = R.string.share_article)) { navHostController.popBackStack() }
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
}