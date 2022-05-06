package com.btpj.wanandroid.ui.collect.article

import com.alibaba.android.arouter.launcher.ARouter
import com.btpj.wanandroid.R
import com.btpj.lib_base.data.bean.CollectArticle
import com.btpj.lib_base.data.local.Constants
import com.btpj.wanandroid.databinding.ListItemCollectArticleBinding
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

            clItem.setOnClickListener {
                ARouter.getInstance().build(Constants.ROUTER_WEB_WEB_ACTIVITY)
                    .withParcelable(Constants.ROUTER_WEB_EXTRA_COLLECT_ARTICLE, item)
                    .navigation()
            }
        }
    }
}