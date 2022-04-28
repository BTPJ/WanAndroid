package com.btpj.wanandroid.ui.web

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.btpj.lib_base.ext.initClose
import com.btpj.lib_base.ext.initTitle
import com.btpj.wanandroid.R
import com.btpj.wanandroid.base.App
import com.btpj.wanandroid.base.BaseActivity
import com.btpj.wanandroid.data.bean.*
import com.btpj.wanandroid.databinding.ActivityWebBinding
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient

/**
 * WebView，用的第三方更强大的agentWeb
 *
 * @author LTP 2022/4/2
 */
class WebActivity : BaseActivity<WebViewModel, ActivityWebBinding>(R.layout.activity_web) {

    private lateinit var mAgentWeb: AgentWeb

    private var mArticle: Article? = null
    private var mCollectArticle: CollectArticle? = null
    private var mCollectUrl: CollectUrl? = null
    private var mBanner: Banner? = null

    private lateinit var mUrl: String
    private var mCollect = false
    private lateinit var mTitle: String

    companion object {
        private const val EXTRA_ARTICLE = "extra_article"
        private const val EXTRA_COLLECT_ARTICLE = "extra_collect_article"
        private const val EXTRA_COLLECT_URL = "extra_collect_url"
        private const val EXTRA_BANNER = "extra_banner"

        /**
         * 页面跳转
         *
         * @param context Context
         * @param article Article
         */
        fun launch(context: Context, article: Article) {
            context.startActivity(Intent(context, WebActivity::class.java).apply {
                putExtra(EXTRA_ARTICLE, article)
            })
        }

        /**
         * 页面跳转
         *
         * @param context Context
         * @param collectArticle CollectArticle
         */
        fun launch(context: Context, collectArticle: CollectArticle) {
            context.startActivity(Intent(context, WebActivity::class.java).apply {
                putExtra(EXTRA_COLLECT_ARTICLE, collectArticle)
            })
        }

        /**
         * 页面跳转
         *
         * @param context Context
         * @param collectUrl CollectUrl
         */
        fun launch(context: Context, collectUrl: CollectUrl) {
            context.startActivity(Intent(context, WebActivity::class.java).apply {
                putExtra(EXTRA_COLLECT_URL, collectUrl)
            })
        }

        /**
         * 页面跳转
         *
         * @param context Context
         * @param banner Banner
         */
        fun launch(context: Context, banner: Banner) {
            context.startActivity(Intent(context, WebActivity::class.java).apply {
                putExtra(EXTRA_BANNER, banner)
            })
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.toolbar.apply {
            setSupportActionBar(this)
            initTitle("加载中...")
            initClose { onBackPressed() }
        }

        intent.apply {
            mArticle = getParcelableExtra(EXTRA_ARTICLE)
            mCollectArticle = getParcelableExtra(EXTRA_COLLECT_ARTICLE)
            mCollectUrl = getParcelableExtra(EXTRA_COLLECT_URL)
            mBanner = getParcelableExtra(EXTRA_BANNER)
        }

        when {
            mArticle != null -> {
                mUrl = mArticle!!.link
                mCollect = mArticle!!.collect
            }
            mCollectArticle != null -> {
                mUrl = mCollectArticle!!.link
                mCollect = true
            }
            mCollectUrl != null -> {
                mUrl = mCollectUrl!!.link
                mCollect = true
            }
            else -> {
                mUrl = mBanner!!.url
                mCollect = false // 无法得知是否收藏直接置为false
            }
        }

        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(mBinding.flWeb, FrameLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebChromeClient(object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    mTitle = title ?: ""
                    mBinding.toolbar.title = mTitle
                }
            })
            .createAgentWeb()
            .ready()
            .go(mUrl)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_web, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.item_collect)?.icon = ContextCompat.getDrawable(
            this,
            if (mCollect) R.drawable.ic_collect else R.drawable.ic_un_collect
        )
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 注意：item.setIcon(R.drawable.ic_un_collect)这样设置一点击菜单就恢复成onPrepareOptionsMenu的初始状态
        when (item.itemId) {
            R.id.item_collect -> { // 收藏
                handleCollect()
            }
            R.id.item_share -> { // 分享
                startActivity(Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "${mTitle}:$mUrl")
                    type = "text/plain"
                }, "分享到"))
            }
            R.id.item_refresh -> { // 刷新
                mAgentWeb.urlLoader.reload()
            }
            R.id.item_openByBrowser -> { // 用默认浏览器打开
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mUrl)))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /** 收藏与取消收藏处理 */
    private fun handleCollect() {
        when {
            mArticle != null -> {
                if (mCollect) {
                    mViewModel.unCollectArticle(mArticle!!.id) {
                        // 重置mCollect为false然后强制刷新menu即可
                        mCollect = !mCollect
                        invalidateOptionsMenu()
                        App.appViewModel.collectEvent.setValue(
                            CollectData(mArticle!!.id, collect = false)
                        )
                    }
                } else {
                    mViewModel.collectArticle(mArticle!!.id) {
                        mCollect = !mCollect
                        invalidateOptionsMenu()
                        App.appViewModel.collectEvent.setValue(
                            CollectData(mArticle!!.id, collect = true)
                        )
                    }
                }
            }
            mCollectArticle != null -> {
                if (mCollect) {
                    mViewModel.unCollectArticle(mCollectArticle!!.originId) {
                        mCollect = !mCollect
                        invalidateOptionsMenu()
                        App.appViewModel.collectEvent.setValue(
                            CollectData(mCollectArticle!!.id, collect = false)
                        )
                    }
                } else {
                    mViewModel.collectArticle(mCollectArticle!!.originId) {
                        mCollect = !mCollect
                        invalidateOptionsMenu()
                        App.appViewModel.collectEvent.setValue(
                            CollectData(mCollectArticle!!.id, collect = true)
                        )
                    }
                }
            }
            mCollectUrl != null -> {
                if (mCollect) {
                    mViewModel.unCollectUrl(mCollectUrl!!.id) {
                        mCollect = !mCollect
                        invalidateOptionsMenu()
                        // 连续收藏取消收藏，id会变这里直接以link为唯一标志
                        App.appViewModel.collectEvent.setValue(
                            CollectData(mCollectUrl!!.id, mCollectUrl!!.link, false)
                        )
                    }
                } else {
                    mViewModel.collectUrl(mCollectUrl!!.name, mCollectUrl!!.link) {
                        mCollect = !mCollect
                        invalidateOptionsMenu()
                        // 取消后再重新收藏id会变，故重新赋值
                        mCollectUrl = it
                        App.appViewModel.collectEvent.setValue(
                            CollectData(mCollectUrl!!.id, mCollectUrl!!.link, true)
                        )
                    }
                }
            }
            else -> {
                if (mCollect) {
                    mViewModel.unCollectUrl(mBanner!!.id) {
                        // 重置mCollect为false然后强制刷新menu即可
                        mCollect = !mCollect
                        invalidateOptionsMenu()
                    }
                } else {
                    mViewModel.collectUrl(mBanner!!.title, mBanner!!.url) {
                        mCollect = !mCollect
                        invalidateOptionsMenu()
                    }
                }
            }
        }
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }
}
