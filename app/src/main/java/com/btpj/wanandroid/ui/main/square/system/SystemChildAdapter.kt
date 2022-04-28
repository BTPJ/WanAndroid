package com.btpj.wanandroid.ui.main.square.system

import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.Classify
import com.btpj.wanandroid.databinding.ListItemTvBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 体系分类列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class SystemChildAdapter :
    BaseQuickAdapter<Classify, BaseDataBindingHolder<ListItemTvBinding>>(layoutResId = R.layout.list_item_tv),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<ListItemTvBinding>, item: Classify) {
        holder.dataBinding?.apply {
            text = item.name
            executePendingBindings()
        }
    }
}