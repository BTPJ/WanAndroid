package com.btpj.wanandroid.ui.splash

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.btpj.lib_base.ext.load
import com.btpj.wanandroid.data.bean.Banner
import com.youth.banner.adapter.BannerAdapter

/**
 * 图片轮播Banner的Adapter
 *
 * @author LTP 2022/3/24
 */
class SplashBannerAdapter(dataList: List<Int>) :
    BannerAdapter<Int, SplashBannerAdapter.BannerViewHolder>(dataList) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent?.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder, data: Int, position: Int, size: Int) {
        holder.imageView.setImageResource(data)
    }

    inner class BannerViewHolder(var imageView: ImageView) : RecyclerView.ViewHolder(imageView)
}
