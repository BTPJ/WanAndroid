package com.btpj.lib_base.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author LTP  2024/1/4
 */
@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
    loadingText: String = "加载中...",
    onDismiss: () -> Unit
) {
    AlertDialog(
        modifier = modifier.size(110.dp),
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    loadingText,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                )
            }
        },
        text = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(30.dp))
            }
        })
}