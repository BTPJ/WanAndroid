package com.btpj.module_web.ui.web

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.btpj.lib_base.base.App
import com.btpj.lib_base.base.BaseVMBActivity
import com.btpj.lib_base.data.bean.*
import com.btpj.lib_base.data.local.Constants
import com.btpj.lib_base.ext.initClose
import com.btpj.lib_base.ext.initTitle
import com.btpj.module_web.R
import com.btpj.module_web.databinding.WebActivityWebBinding
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient

/**
 * WebView，用的第三方更强大的agentWeb
 *
 * @author LTP 2022/4/2
 */
@Route(path = Constants.ROUTER_WEB_WEB_ACTIVITY)
class WebActivity :
    BaseVMBActivity<WebViewModel, WebActivityWebBinding>(R.layout.web_activity_web) {

    private lateinit var mAgentWeb: AgentWeb

    @Autowired(name = Constants.ROUTER_WEB_EXTRA_ARTICLE)
    @JvmField
    var mArticle: Article? = null

    @Autowired(name = Constants.ROUTER_WEB_EXTRA_COLLECT_ARTICLE)
    @JvmField
    var mCollectArticle: CollectArticle? = null

    @Autowired(name = Constants.ROUTER_WEB_EXTRA_COLLECT_URL)
    @JvmField
    var mCollectUrl: CollectUrl? = null

    @Autowired(name = Constants.ROUTER_WEB_EXTRA_BANNER)
    @JvmField
    var mBanner: Banner? = null

    private lateinit var mUrl: String
    private var mCollect = false
    private lateinit var mTitle: String

    override fun initView(savedInstanceState: Bundle?) {
        // 自动完成参数注入
        ARouter.getInstance().inject(this)

        mBinding.toolbar.apply {
            setSupportActionBar(this)
            initTitle("加载中...")
            initClose { onBackPressed() }
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
        menuInflater.inflate(R.menu.web_menu_web, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.item_collect)?.icon = ContextCompat.getDrawable(
            this,
            if (mCollect) com.btpj.lib_base.R.drawable.ic_collect else com.btpj.lib_base.R.drawable.ic_un_collect
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
