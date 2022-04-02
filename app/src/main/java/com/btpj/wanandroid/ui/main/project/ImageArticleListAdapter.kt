package com.btpj.wanandroid.ui.main.project

import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.databinding.ListItemArticleImageBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 带图片的文章列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class ImageArticleListAdapter :
    BaseQuickAdapter<Article, BaseDataBindingHolder<ListItemArticleImageBinding>>(layoutResId = R.layout.list_item_article_image),
    LoadMoreModule {

    override fun convert(holder: BaseDataBindingHolder<ListItemArticleImageBinding>, item: Article) {
        holder.dataBinding?.apply {
            article = item
            executePendingBindings()
        }
    }
}