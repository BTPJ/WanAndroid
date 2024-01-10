package com.btpj.wanandroid.ui.main.square.system

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
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

/**
 * @author LTP  2023/12/25
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SystemPage(
    lazyListState: LazyListState,
    systemViewModel: SystemViewModel = viewModel(),
    onStructureClick: (Classify) -> Unit
) {
    val uiState by systemViewModel.uiState.observeAsState()

    val pullRefreshState =
        rememberPullRefreshState(refreshing = uiState?.showLoading ?: false, onRefresh = {
            systemViewModel.fetchSystemList()
        })

    LaunchedEffect(true) {
        if (uiState?.data == null) {
            systemViewModel.fetchSystemList()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            uiState?.data?.let {
                items(it, key = { item -> item.id }) { structure ->
                    StructureItem(structure, onStructureClick)
                }
            }
        }
        PullRefreshIndicator(
            refreshing = uiState?.showLoading ?: false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
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
