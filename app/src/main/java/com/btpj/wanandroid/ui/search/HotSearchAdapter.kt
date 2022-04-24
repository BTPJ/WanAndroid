package com.btpj.wanandroid.ui.search

import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.HotSearch
import com.btpj.wanandroid.databinding.ListItemTvBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 热门搜索列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class HotSearchAdapter :
    BaseQuickAdapter<HotSearch, BaseDataBindingHolder<ListItemTvBinding>>(layoutResId = R.layout.list_item_tv),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<ListItemTvBinding>, item: HotSearch) {
        holder.dataBinding?.apply {
            text = item.name
            executePendingBindings()
        }
    }
}