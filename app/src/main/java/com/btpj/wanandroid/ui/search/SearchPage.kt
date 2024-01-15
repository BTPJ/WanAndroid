package com.btpj.wanandroid.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.lib_base.utils.CommonUtil
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.local.CacheManager

/**
 * @author LTP  2023/12/25
 */
@Composable
fun SearchPage(
    navHostController: NavHostController,
    searchViewModel: SearchViewModel = viewModel(),
    toSearchResultPage: (String) -> Unit
) {
    Column {
        TitleContent(navHostController, searchViewModel) { toSearchResultPage(it) }
        HotSearchContent(searchViewModel) { toSearchResultPage(it) }
        SearchHistoryContent(searchViewModel) { toSearchResultPage(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleContent(
    navHostController: NavHostController,
    searchViewModel: SearchViewModel,
    onSearchClick: (String) -> Unit
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = Unit, block = {
        focusRequester.requestFocus()
    })

    Box(contentAlignment = Alignment.Center) {
        val searchAble = searchText.trim().isNotEmpty()
        TitleBar(menu = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary.copy(if (searchAble) 1f else 0.4f),
                modifier = Modifier
                    .size(30.dp)
                    .clickable(enabled = searchAble) {
                        searchViewModel.handleSearchClick(searchText)
                        onSearchClick(searchText)
                    }
            )
        }) {
            navHostController.popBackStack()
        }
        TextField(
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .focusRequester(focusRequester),
            value = searchText,
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.input_search_key_to_search),
                    color = MaterialTheme.colorScheme.onPrimary.copy(0.5f)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.onPrimary.copy(0.5f)
            ),
            onValueChange = { value -> searchText = value })
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HotSearchContent(searchViewModel: SearchViewModel, onHotSearchClick: (String) -> Unit) {
    val hotSearchList by searchViewModel.hotSearchList.observeAsState()

    LaunchedEffect(key1 = Unit, block = {
        searchViewModel.fetchHotSearchList()
    })

    Text(
        text = "热门搜索",
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
    )
    FlowRow(modifier = Modifier.padding(horizontal = 10.dp)) {
        hotSearchList?.forEach {
            Text(
                modifier = Modifier
                    .padding(4.dp)
                    .clickable {
                        onHotSearchClick(it.name)
                    }
                    .background(LocalContentColor.current.copy(0.1f))
                    .padding(vertical = 6.dp, horizontal = 10.dp),
                text = it.name,
                color = CommonUtil.randomColor(),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun SearchHistoryContent(
    searchViewModel: SearchViewModel,
    onHistoryClick: (String) -> Unit
) {
    val searchHistoryList by searchViewModel.searchHistoryData.observeAsState()

    LaunchedEffect(key1 = Unit, block = {
        searchViewModel.fetchSearchHistoryData()
    })

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Text(
            text = "历史搜索",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        Text(
            text = "清空",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable { searchViewModel.clearHistory() })
    }
    searchHistoryList?.apply {
        CacheManager.saveSearchHistoryData(this)
        forEach {
            SearchHistoryItem(
                it,
                onDeleteClick = { hisItem -> searchViewModel.handleDeleteHistoryItem(hisItem) }) { hisItem ->
                searchViewModel.handleHistoryItemClick(hisItem)
                onHistoryClick(it)
            }
        }
    }
}

@Composable
fun SearchHistoryItem(
    item: String,
    onDeleteClick: (String) -> Unit,
    onItemClick: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .padding(12.dp)
    ) {
        Text(
            text = item,
            color = MaterialTheme.colorScheme.onSurface
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier.clickable { onDeleteClick(item) }
        )
    }
}
