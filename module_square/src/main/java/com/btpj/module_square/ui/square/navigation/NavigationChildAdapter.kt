package com.btpj.module_square.ui.square.navigation

import com.btpj.lib_base.data.bean.Article
import com.btpj.lib_base.databinding.CommonListItemTvBinding
import com.btpj.lib_base.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 导航分类列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class NavigationChildAdapter :
    BaseQuickAdapter<Article, BaseDataBindingHolder<CommonListItemTvBinding>>(layoutResId = R.layout.common_list_item_tv),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<CommonListItemTvBinding>, item: Article) {
        holder.dataBinding?.apply {
            text = item.title
            executePendingBindings()
        }
    }
}