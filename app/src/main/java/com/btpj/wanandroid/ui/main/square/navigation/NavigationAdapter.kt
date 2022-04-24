package com.btpj.wanandroid.ui.main.square.navigation

import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.Navigation
import com.btpj.wanandroid.databinding.ListItemSystemBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.google.android.flexbox.FlexboxLayoutManager

/**
 * 导航列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class NavigationAdapter :
    BaseQuickAdapter<Navigation, BaseDataBindingHolder<ListItemSystemBinding>>(layoutResId = R.layout.list_item_system),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<ListItemSystemBinding>, item: Navigation) {
        holder.dataBinding?.apply {
            text = item.name
            executePendingBindings()

            recyclerView.apply {
                layoutManager = FlexboxLayoutManager(context)
                setHasFixedSize(true) // 当确定Item的改变不会影响RecyclerView的宽高的时候可设置以提升性能
                setItemViewCacheSize(200) // 设置缓存大小为200，默认为2
                adapter = NavigationChildAdapter().apply {
                    setList(item.articles)
                }
            }
        }
    }
}