package com.btpj.wanandroid.ui.collect.url

import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.CollectUrl
import com.btpj.wanandroid.databinding.ListItemCollectUrlBinding
import com.btpj.wanandroid.ui.web.WebActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 收藏网址列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class CollectUrlAdapter :
    BaseQuickAdapter<CollectUrl, BaseDataBindingHolder<ListItemCollectUrlBinding>>(layoutResId = R.layout.list_item_collect_url) {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<ListItemCollectUrlBinding>, item: CollectUrl) {
        holder.dataBinding?.apply {
            collectUrl = item
            executePendingBindings()

            clItem.setOnClickListener { WebActivity.launch(context, item) }
        }
    }
}