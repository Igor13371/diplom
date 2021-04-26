package com.example.marketing.admins

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marketing.AbsAdapter
import com.example.marketing.BR
import com.example.marketing.R
import com.example.marketing.database.ChoosenService
import com.example.marketing.databinding.ItemServiceBinding
import com.example.teacherhelper.utils.inflate

class ServiceAdapter : AbsAdapter<ChoosenService, ServiceAdapter.GroupViewHolder>() {

    private var onClickListener: OnClickListener<ChoosenService>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(
            parent.context.inflate(
                R.layout.item_chosen_servise,
                parent,
                false
            )
        )
    }

    fun onItemClicked(view: View, service: ChoosenService) {
        onClickListener?.onClick(getItemPosition(service), view, service)
    }

    fun setOnItemClickListener(callback: (Int, ChoosenService) -> Unit) {
        onClickListener = object : OnClickListener<ChoosenService> {
            override fun onClick(position: Int, view: View, item: ChoosenService) {
                callback(position, item)
            }
        }
    }


    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemServiceBinding? = DataBindingUtil.bind(itemView)
            private set

        fun bind(model: ChoosenService?) {
            if (model != null) {
                binding?.setVariable(BR.model, model)
                binding?.setVariable(BR.adapter, this@ServiceAdapter)
                binding?.executePendingBindings()
            }
        }
    }


}