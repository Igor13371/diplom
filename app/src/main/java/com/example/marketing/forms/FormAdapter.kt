package com.example.marketing.forms

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marketing.AbsAdapter
import com.example.marketing.BR
import com.example.marketing.R
import com.example.marketing.database.Offer
import com.example.marketing.databinding.ItemServiceBinding
import com.example.teacherhelper.utils.inflate

class FormAdapter : AbsAdapter<Offer, FormAdapter.GroupViewHolder>() {

    private var onClickListener: OnClickListener<Offer>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(
            parent.context.inflate(
                R.layout.item_service,
                parent,
                false
            )
        )
    }

    fun onItemClicked(view: View, group: Offer) {
        onClickListener?.onClick(getItemPosition(group), view, group)
    }

    fun setOnItemClickListener(callback: (Int, Offer) -> Unit) {
        onClickListener = object : OnClickListener<Offer> {
            override fun onClick(position: Int, view: View, item: Offer) {
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

        fun bind(model: Offer?) {
            if (model != null) {
                binding?.setVariable(BR.model, model)
                binding?.setVariable(BR.adapter, this@FormAdapter)
                binding?.executePendingBindings()
            }
        }
    }


}