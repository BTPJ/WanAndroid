package com.btpj.wanandroid.ui.ip

import com.btpj.wanandroid.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 历史IP列表的Adapter
 *
 * @author LTP  2017/11/3
 * @param currentIp 当前IP
 */
class IpListAdapter(private val currentIp: String) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.list_item_ip) {

    override fun convert(holder: BaseViewHolder, item: String) {
//        holder.setText(R.id.tv_ip, item)
//                .setVisible(R.id.iv_delete, item != currentIp)
//                // 当前IP地址不显示删除按键
//                .getView<View>(R.id.iv_delete).setOnClickListener {
//                    data.remove(item)
//                    SharedPreferencesUtil.writeData(context, "ipList", Gson().toJson(data))
//                    notifyDataSetChanged()
//                }
    }
}
