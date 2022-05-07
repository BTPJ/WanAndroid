package com.btpj.wanandroid.ui.search

import android.view.View
import com.btpj.wanandroid.R
import com.btpj.wanandroid.databinding.ListItemSearchBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 搜索历史列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class SearchHistoryAdapter :
    BaseQuickAdapter<String, BaseDataBindingHolder<ListItemSearchBinding>>(layoutResId = R.layout.list_item_search),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<ListItemSearchBinding>, item: String) {
        holder.dataBinding?.apply {
            this.item = item
            executePendingBindings()

            viewLine.visibility = View.GONE
        }
    }
}