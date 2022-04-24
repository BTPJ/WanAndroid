package com.btpj.wanandroid.ui.integral.record

import android.annotation.SuppressLint
import com.btpj.wanandroid.R
import com.btpj.wanandroid.data.bean.IntegralRecord
import com.btpj.wanandroid.databinding.ListItemIntegralRecordBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 积分记录列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class IntegralRecordAdapter :
    BaseQuickAdapter<IntegralRecord, BaseDataBindingHolder<ListItemIntegralRecordBinding>>(
        layoutResId = R.layout.list_item_integral_record
    ), LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    @SuppressLint("SetTextI18n")
    override fun convert(
        holder: BaseDataBindingHolder<ListItemIntegralRecordBinding>,
        item: IntegralRecord
    ) {
        holder.dataBinding?.apply {
            integralRecord = item
            executePendingBindings()
            val descStr =
                if (item.desc.contains("积分"))
                    item.desc.subSequence(item.desc.indexOf("积分"), item.desc.length)
                else ""
            tvTitle.text = "${item.reason}${descStr}"
        }
    }
}