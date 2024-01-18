package com.btpj.module_square.ui.square.system

import com.btpj.lib_base.R
import com.btpj.lib_base.data.bean.Classify
import com.btpj.lib_base.databinding.CommonListItemTvBinding
import com.btpj.module_square.ui.square.system.details.SystemArticleListActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 体系分类列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class SystemChildAdapter :
    BaseQuickAdapter<Classify, BaseDataBindingHolder<CommonListItemTvBinding>>(layoutResId = R.layout.common_list_item_tv),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<CommonListItemTvBinding>, item: Classify) {
        holder.dataBinding?.apply {
            text = item.name
            executePendingBindings()
        }
    }
}