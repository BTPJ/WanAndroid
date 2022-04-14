package com.btpj.wanandroid.ui.web

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.widget.FrameLayout
import com.btpj.wanandroid.base.BaseActivity
import com.btpj.lib_base.ext.initClose
import com.btpj.lib_base.ext.initTitle
import com.btpj.wanandroid.R
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

    private lateinit var mUrl: String
    private lateinit var mTitle: String

    companion object {
        private const val EXTRA_URL = "url"

        /**
         * 页面跳转
         *
         * @param context Context
         * @param url web页面链接
         */
        fun launch(context: Context, url: String) {
            context.startActivity(Intent(context, WebActivity::class.java).apply {
                putExtra(EXTRA_URL, url)
            })
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(mBinding.toolbar.apply { initTitle("加载中...") })
        // 返回点击事件必须在setSupportActionBar方法调用之后
        mBinding.toolbar.initClose { onBackPressed() }

        mUrl = intent.getStringExtra(EXTRA_URL) ?: ""

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_web, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_collect -> { // 收藏

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
