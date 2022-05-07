package com.btpj.module_mine.ui.ip

import android.view.View
import com.btpj.lib_base.data.local.IpManager
import com.btpj.module_mine.R
import com.btpj.module_mine.databinding.MineListItemIpBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 历史IP列表的Adapter
 *
 * @author LTP  2017/11/3
 * @param currentIp 当前IP
 */
class IpListAdapter(private val currentIp: String) :
    BaseQuickAdapter<String, BaseDataBindingHolder<MineListItemIpBinding>>(R.layout.mine_list_item_ip) {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<MineListItemIpBinding>, item: String) {
        holder.dataBinding?.apply {
            this.item = item
            executePendingBindings()

            ivDelete.visibility = if (item != currentIp) View.VISIBLE else View.GONE
            ivDelete.setOnClickListener {
                data.remove(item)
                notifyItemRemoved(holder.layoutPosition)
                IpManager.saveIPSet(HashSet(data))
            }
        }
    }
}
