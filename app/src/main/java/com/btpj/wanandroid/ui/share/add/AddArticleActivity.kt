package com.btpj.wanandroid.ui.share.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.btpj.lib_base.base.BaseVMBActivity
import com.btpj.lib_base.utils.ToastUtil
import com.btpj.wanandroid.base.App
import com.btpj.wanandroid.R
import com.btpj.wanandroid.databinding.ActivityAddArticleBinding

/**
 * 添加分享的文章
 *
 * @author LTP 2022/4/12
 */
class AddArticleActivity :
    BaseVMBActivity<AddArticleViewModel, ActivityAddArticleBinding>(R.layout.activity_add_article) {

    companion object {

        /**
         * 页面启动
         * @param context Context
         */
        fun launch(context: Context) {
            context.startActivity(Intent(context, AddArticleActivity::class.java))
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.apply {
            titleLayout.setRightView(R.drawable.ic_integral_rule)

            btnShare.setOnClickListener {
                when {
                    mViewModel.title.get().isNullOrEmpty() -> {
                        ToastUtil.showShort(this@AddArticleActivity, "请填写文章标题")
                    }

                    mViewModel.articleLink.get().isNullOrEmpty() -> {
                        ToastUtil.showShort(this@AddArticleActivity, "请填写文章链接")
                    }

                    else -> {
                        mViewModel.apply {
                            addArticle(title.get()!!, articleLink.get()!!) {
                                App.appViewModel.shareArticleEvent.setValue(true)
                                onBackPressed()
                            }
                        }
                    }
                }
            }
        }
    }
}