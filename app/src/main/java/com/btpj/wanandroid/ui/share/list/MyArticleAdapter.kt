package com.btpj.wanandroid.ui.share.list

import android.annotation.SuppressLint
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.Article
import com.btpj.wanandroid.databinding.ListItemShareArticleBinding
import com.btpj.wanandroid.ui.web.WebActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 积分记录列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class MyArticleAdapter :
    BaseQuickAdapter<Article, BaseDataBindingHolder<ListItemShareArticleBinding>>(
        layoutResId = R.layout.list_item_share_article
    ), LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    @SuppressLint("SetTextI18n")
    override fun convert(
        holder: BaseDataBindingHolder<ListItemShareArticleBinding>,
        item: Article
    ) {
        holder.dataBinding?.apply {
            article = item
            executePendingBindings()

            clItem.setOnClickListener { WebActivity.launch(context, item) }
        }
    }
}