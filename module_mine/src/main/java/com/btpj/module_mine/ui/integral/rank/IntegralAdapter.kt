package com.btpj.module_mine.ui.integral.rank

import com.btpj.lib_base.data.bean.CoinInfo
import com.btpj.module_mine.R
import com.btpj.module_mine.databinding.MineListItemIntegralBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 积分排行列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class IntegralAdapter :
    BaseQuickAdapter<CoinInfo, BaseDataBindingHolder<MineListItemIntegralBinding>>(
        layoutResId = R.layout.mine_list_item_integral
    ),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(
        holder: BaseDataBindingHolder<MineListItemIntegralBinding>,
        item: CoinInfo
    ) {
        holder.dataBinding?.apply {
            integral = item
            executePendingBindings()
        }
    }
}