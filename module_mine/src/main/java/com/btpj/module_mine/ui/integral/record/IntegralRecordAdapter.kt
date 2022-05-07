package com.btpj.module_mine.ui.integral.record

import android.annotation.SuppressLint
import com.btpj.lib_base.data.bean.IntegralRecord
import com.btpj.module_mine.R
import com.btpj.module_mine.databinding.MineListItemIntegralRecordBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 积分记录列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class IntegralRecordAdapter :
    BaseQuickAdapter<IntegralRecord, BaseDataBindingHolder<MineListItemIntegralRecordBinding>>(
        layoutResId = R.layout.mine_list_item_integral_record
    ), LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    @SuppressLint("SetTextI18n")
    override fun convert(
        holder: BaseDataBindingHolder<MineListItemIntegralRecordBinding>,
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