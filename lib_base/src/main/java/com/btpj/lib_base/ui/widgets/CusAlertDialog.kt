package com.btpj.lib_base.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @author LTP  2024/1/4
 */
@Composable
fun CusAlertDialog(
    modifier: Modifier = Modifier,
    titleText: String = "温馨提示",
    content: @Composable () -> Unit,
    confirmText: String = "确定",
    onConfirm: () -> Unit = {},
    showCancel: Boolean = true,
    cancelText: String = "取消",
    onCancel: () -> Unit = {},
    onDismiss: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = { onDismiss() },
        title = { Text(text = titleText) },
        text = content,
        confirmButton = {
            Text(text = confirmText, modifier = Modifier.clickable {
                onDismiss()
                onConfirm()
            })
        },
        dismissButton = {
            if (showCancel) {
                Text(text = cancelText, modifier = Modifier.clickable {
                    onDismiss()
                    onCancel()
                })
            }
        })
}