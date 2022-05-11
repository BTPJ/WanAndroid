package com.btpj.module_mine.ui.collect.url

import com.btpj.lib_base.data.bean.CollectUrl
import com.btpj.lib_base.export.ModuleWebApi
import com.btpj.module_mine.R
import com.btpj.module_mine.databinding.MineListItemCollectUrlBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 收藏网址列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class CollectUrlAdapter :
    BaseQuickAdapter<CollectUrl, BaseDataBindingHolder<MineListItemCollectUrlBinding>>(layoutResId = R.layout.mine_list_item_collect_url) {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(
        holder: BaseDataBindingHolder<MineListItemCollectUrlBinding>,
        item: CollectUrl
    ) {
        holder.dataBinding?.apply {
            collectUrl = item
            executePendingBindings()

            clItem.setOnClickListener {
                ModuleWebApi.navToWebActivity(item)
            }
        }
    }
}