package com.btpj.module_home.ui.search

import com.btpj.lib_base.data.bean.HotSearch
import com.btpj.lib_base.databinding.CommonListItemTvBinding
import com.btpj.lib_base.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 热门搜索列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class HotSearchAdapter :
    BaseQuickAdapter<HotSearch, BaseDataBindingHolder<CommonListItemTvBinding>>(layoutResId = R.layout.common_list_item_tv),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<CommonListItemTvBinding>, item: HotSearch) {
        holder.dataBinding?.apply {
            text = item.name
            executePendingBindings()
        }
    }
}