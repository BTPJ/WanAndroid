package com.btpj.wanandroid.ui.integral.rank

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.btpj.lib_base.ext.navigate
import com.btpj.lib_base.ui.widgets.RefreshList
import com.btpj.lib_base.ui.widgets.TitleBar
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.CoinInfo
import com.btpj.wanandroid.navigation.Route

/**
 *  积分排名
 * @author LTP  2024/1/11
 */
@Composable
fun IntegralRankPage(
    navHostController: NavHostController,
    integralRankViewModel: IntegralRankViewModel = hiltViewModel()
) {

    val myCoinInfo by integralRankViewModel.myCoinInfo.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        integralRankViewModel.fetchMyCoinInfo()
    })

    Column {
        TitleBar(title = "积分排行", menu = {
            Icon(
                painter = painterResource(id = R.drawable.ic_integral_rule),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.clickable {
                    navHostController.navigate(
                        Route.WEB,
                        bundleOf("url" to "https://www.wanandroid.com/blog/show/2653")
                    )
                }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_integral_record),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable { navHostController.navigate(Route.INTEGRAL_RANK_RECORD) }
            )
        }) { navHostController.popBackStack() }
        RefreshList(
            modifier = Modifier.weight(1f),
            uiState = integralRankViewModel.uiState.collectAsState().value,
            onRefresh = { integralRankViewModel.fetchIntegralRankList() },
            onLoadMore = { integralRankViewModel.fetchIntegralRankList(false) }) {
            CoinInfoItem(it)
        }
        if (myCoinInfo != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = myCoinInfo!!.rank,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = myCoinInfo!!.username,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = myCoinInfo!!.coinCount.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun CoinInfoItem(coinInfo: CoinInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(text = coinInfo.rank, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                text = coinInfo.username,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                fontSize = 16.sp
            )
            Text(
                text = coinInfo.coinCount.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
