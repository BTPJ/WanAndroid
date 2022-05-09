package com.btpj.module_project.ui.project

import com.alibaba.android.arouter.launcher.ARouter
import com.btpj.lib_base.data.bean.Article
import com.btpj.lib_base.data.local.Constants
import com.btpj.module_project.R
import com.btpj.module_project.databinding.ProjectListItemArticleImageBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 带图片的文章列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class ImageArticleAdapter :
    BaseQuickAdapter<Article, BaseDataBindingHolder<ProjectListItemArticleImageBinding>>(layoutResId = R.layout.project_list_item_article_image),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(
        holder: BaseDataBindingHolder<ProjectListItemArticleImageBinding>,
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