package com.btpj.lib_base.adapter

import com.alibaba.android.arouter.launcher.ARouter
import com.btpj.lib_base.R
import com.btpj.lib_base.data.bean.Article
import com.btpj.lib_base.data.local.Constants
import com.btpj.lib_base.databinding.CommonListItemArticleBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 文章列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class ArticleAdapter :
    BaseQuickAdapter<Article, BaseDataBindingHolder<CommonListItemArticleBinding>>(R.layout.common_list_item_article),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<CommonListItemArticleBinding>, item: Article) {
        holder.dataBinding?.apply {
            article = item
            executePendingBindings()

            clItem.setOnClickListener {
                ARouter.getInstance().build(Constants.ROUTER_WEB_WEB_ACTIVITY)
                    .withParcelable(Constants.ROUTER_WEB_EXTRA_ARTICLE, item)
                    .navigation()
            }
        }
    }
}