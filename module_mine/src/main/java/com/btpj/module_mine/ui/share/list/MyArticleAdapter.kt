package com.btpj.module_mine.ui.share.list

import android.annotation.SuppressLint
import com.alibaba.android.arouter.launcher.ARouter
import com.btpj.lib_base.data.bean.Article
import com.btpj.lib_base.data.local.Constants
import com.btpj.module_mine.R
import com.btpj.module_mine.databinding.MineListItemShareArticleBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 积分记录列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class MyArticleAdapter :
    BaseQuickAdapter<Article, BaseDataBindingHolder<MineListItemShareArticleBinding>>(
        layoutResId = R.layout.mine_list_item_share_article
    ), LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    @SuppressLint("SetTextI18n")
    override fun convert(
        holder: BaseDataBindingHolder<MineListItemShareArticleBinding>,
        item: Article
    ) {
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