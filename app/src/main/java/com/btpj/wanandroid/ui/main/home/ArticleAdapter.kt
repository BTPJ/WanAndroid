package com.btpj.wanandroid.ui.main.home

import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.databinding.ListItemArticleBinding
import com.btpj.wanandroid.ui.web.WebActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 文章列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class ArticleAdapter :
    BaseQuickAdapter<Article, BaseDataBindingHolder<ListItemArticleBinding>>(R.layout.list_item_article),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<ListItemArticleBinding>, item: Article) {
        holder.dataBinding?.apply {
            article = item
            executePendingBindings()

            clItem.setOnClickListener { WebActivity.launch(context, item) }
        }
    }
}