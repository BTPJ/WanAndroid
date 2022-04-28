package com.btpj.wanandroid.ui.main.square.navigation

import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.databinding.ListItemTvBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 导航分类列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class NavigationChildAdapter :
    BaseQuickAdapter<Article, BaseDataBindingHolder<ListItemTvBinding>>(layoutResId = R.layout.list_item_tv),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<ListItemTvBinding>, item: Article) {
        holder.dataBinding?.apply {
            text = item.title
            executePendingBindings()
        }
    }
}