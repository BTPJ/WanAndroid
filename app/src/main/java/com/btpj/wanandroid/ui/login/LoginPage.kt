package com.btpj.wanandroid.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.btpj.lib_base.ui.widgets.CoilImage
import com.btpj.lib_base.ui.widgets.LoadingDialog
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.local.UserManager
import com.btpj.wanandroid.navigation.Route

/**
 * @author LTP  2024/1/4
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navHostController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var showLoadingDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit, block = {
        username = UserManager.getLastUserName()
    })

    if (showLoadingDialog) {
        LoadingDialog(loadingText = "登录中...") { showLoadingDialog = false }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TitleBar(title = stringResource(id = R.string.login)) { navHostController.popBackStack() }
        CoilImage(
            model = R.drawable.ic_user_round,
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 48.dp)
                .size(100.dp)
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            value = username,
            label = { Text(text = stringResource(id = R.string.user_name)) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                    0.4f
                )
            ),
            onValueChange = { username = it })
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            value = password,
            label = { Text(text = stringResource(id = R.string.password)) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                    0.4f
                )
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        painter = if (passwordVisibility) painterResource(id = R.drawable.ic_pwd_show)
                        else painterResource(id = R.drawable.ic_pwd_hide),
                        modifier = Modifier.size(25.dp),
                        contentDescription = null
                    )
                }
            },
            onValueChange = { password = it })
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 20.dp, end = 20.dp),
            enabled = username.trim().isNotEmpty() && password.trim().isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.6f),
                disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(0.8f)
            ),
            onClick = {
                // 执行登录操作
                showLoadingDialog = true
                loginViewModel.login(
                    username,
                    password,
                    errorBlock = { showLoadingDialog = false }) {
                    // 登录成功后的操作
                    showLoadingDialog = false
                    navHostController.popBackStack()
                }
            }) {
            Text(text = stringResource(id = R.string.login))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp, top = 10.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = stringResource(id = R.string.register),
                modifier = Modifier.clickable {
                    navHostController.navigate(Route.REGISTER)
                },
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}