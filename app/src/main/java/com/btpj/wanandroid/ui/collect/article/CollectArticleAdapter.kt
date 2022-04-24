package com.btpj.wanandroid.ui.collect.article

import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.CollectArticle
import com.btpj.wanandroid.databinding.ListItemCollectArticleBinding
import com.btpj.wanandroid.ui.web.WebActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 收藏文章列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class CollectArticleAdapter :
    BaseQuickAdapter<CollectArticle, BaseDataBindingHolder<ListItemCollectArticleBinding>>(
        layoutResId = R.layout.list_item_collect_article
    ),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(
        holder: BaseDataBindingHolder<ListItemCollectArticleBinding>,
        item: CollectArticle
    ) {
        holder.dataBinding?.apply {
            collectArticle = item
            executePendingBindings()

            clItem.setOnClickListener { WebActivity.launch(context, item) }
        }
    }
}