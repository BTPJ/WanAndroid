package com.btpj.lib_base.export

import com.alibaba.android.arouter.launcher.ARouter
import com.btpj.lib_base.data.bean.Article
import com.btpj.lib_base.data.bean.Banner
import com.btpj.lib_base.data.bean.CollectArticle
import com.btpj.lib_base.data.bean.CollectUrl

/**
 * module_web模块对外提供的接口管理类
 * 包括：页面跳转、获取实例等
 *
 * @author LTP  2022/5/11
 */
object ModuleWebApi {

    const val ROUTER_WEB_WEB_ACTIVITY = "/web/WebActivity"
    const val ROUTER_WEB_EXTRA_BANNER = "extra_banner"
    const val ROUTER_WEB_EXTRA_ARTICLE = "extra_article"
    const val ROUTER_WEB_EXTRA_COLLECT_ARTICLE = "extra_collect_article"
    const val ROUTER_WEB_EXTRA_COLLECT_URL = "extra_collect_url"

    /**
     * 跳转到web页面
     * @param banner Banner实体
     */
    fun navToWebActivity(banner: Banner) {
        ARouter.getInstance().build(ROUTER_WEB_WEB_ACTIVITY)
            .withParcelable(ROUTER_WEB_EXTRA_BANNER, banner).navigation()
    }

    /**
     * 跳转到web页面
     * @param article Article实体
     */
    fun navToWebActivity(article: Article) {
        ARouter.getInstance().build(ROUTER_WEB_WEB_ACTIVITY)
            .withParcelable(ROUTER_WEB_EXTRA_ARTICLE, article).navigation()
    }

    /**
     * 跳转到web页面
     * @param collectArticle CollectArticle实体
     */
    fun navToWebActivity(collectArticle: CollectArticle) {
        ARouter.getInstance().build(ROUTER_WEB_WEB_ACTIVITY)
            .withParcelable(ROUTER_WEB_EXTRA_COLLECT_ARTICLE, collectArticle).navigation()
    }

    /**
     * 跳转到web页面
     * @param collectUrl CollectUrl实体
     */
    fun navToWebActivity(collectUrl: CollectUrl) {
        ARouter.getInstance().build(ROUTER_WEB_WEB_ACTIVITY)
            .withParcelable(ROUTER_WEB_EXTRA_COLLECT_URL, collectUrl).navigation()
    }
}