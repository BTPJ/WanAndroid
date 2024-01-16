package com.btpj.wanandroid.ui.main.square.system

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.btpj.lib_base.ext.toHtml
import com.btpj.lib_base.utils.CommonUtil
import com.btpj.wanandroid.data.bean.Classify
import com.btpj.wanandroid.data.bean.Structure
import com.btpj.lib_base.ui.widgets.RefreshList

/**
 * @author LTP  2023/12/25
 */
@Composable
fun SystemPage(
    lazyListState: LazyListState,
    systemViewModel: SystemViewModel = viewModel(),
    onStructureClick: (Classify) -> Unit
) {
    RefreshList(
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 10.dp),
        uiState = systemViewModel.uiState.observeAsState().value,
        lazyListState = lazyListState,
        onRefresh = { systemViewModel.fetchSystemList() },
        itemContent = { StructureItem(it, onStructureClick) }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StructureItem(structure: Structure, onStructureClick: (Classify) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = structure.name.toHtml().toString(),
                color = LocalContentColor.current.copy(0.8f),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            FlowRow(modifier = Modifier.padding(top = 10.dp)) {
                structure.children.forEach { classify ->
                    Text(
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onStructureClick.invoke(classify) }
                            .background(LocalContentColor.current.copy(0.1f))
                            .padding(vertical = 6.dp, horizontal = 10.dp),
                        text = classify.name.toHtml().toString(),
                        color = CommonUtil.randomColor(),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
