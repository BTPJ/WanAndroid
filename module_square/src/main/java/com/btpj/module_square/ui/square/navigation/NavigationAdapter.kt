package com.btpj.module_square.ui.square.navigation

import com.btpj.lib_base.data.bean.Navigation
import com.btpj.module_square.R
import com.btpj.module_square.databinding.SquareListItemSystemBinding
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
    BaseQuickAdapter<Navigation, BaseDataBindingHolder<SquareListItemSystemBinding>>(layoutResId = R.layout.square_list_item_system),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(
        holder: BaseDataBindingHolder<SquareListItemSystemBinding>,
        item: Navigation
    ) {
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