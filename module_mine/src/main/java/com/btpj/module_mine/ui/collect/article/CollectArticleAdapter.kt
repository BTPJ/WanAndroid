package com.btpj.module_mine.ui.collect.article

import com.btpj.lib_base.data.bean.CollectArticle
import com.btpj.lib_base.export.ModuleWebApi
import com.btpj.module_mine.R
import com.btpj.module_mine.databinding.MineListItemCollectArticleBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 收藏文章列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class CollectArticleAdapter :
    BaseQuickAdapter<CollectArticle, BaseDataBindingHolder<MineListItemCollectArticleBinding>>(
        layoutResId = R.layout.mine_list_item_collect_article
    ),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(
        holder: BaseDataBindingHolder<MineListItemCollectArticleBinding>,
        item: CollectArticle
    ) {
        holder.dataBinding?.apply {
            collectArticle = item
            executePendingBindings()

            clItem.setOnClickListener {
                ModuleWebApi.navToWebActivity(item)
            }
        }
    }
}