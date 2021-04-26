package com.example.marketing

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbsAdapter<T, VH: RecyclerView.ViewHolder>: RecyclerView.Adapter<VH>() {

    val items: MutableList<T?> = arrayListOf()

    override fun getItemCount(): Int = items.size

    fun isEmpty(): Boolean = items.isEmpty()

    fun isNotEmpty(): Boolean = items.isNotEmpty()

    fun getItem(position: Int): T? = items[position]

    fun getLastItem(): T? = items[items.lastIndex]

    fun getItemPosition(item: T?): Int = items.indexOf(item)

    fun notifyData(anotherItems: List<T>, clear: Boolean = true) {
        if (clear) {
            items.clear()
            items.addAll(anotherItems)
            notifyDataSetChanged()
        } else {
            addItems(anotherItems)
        }
    }

    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }

    fun addItem(position: Int, item: T) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    fun addItems(newItems: List<T>) {
        val positionStart = items.size + 1
        items.addAll(newItems)
        notifyItemRangeInserted(positionStart, newItems.size)
    }

    fun updateItem(item: T) {
        if (items.isNotEmpty()) {
            val position = getItemPosition(item)
            if (position > -1) notifyItemChanged(position)
        }
    }

    fun removeItem(item: T) {
        if (items.isNotEmpty()) {
            val position = items.indexOf(item)
            if (position > -1) removeItem(position)
        }
    }

    fun removeItem(position: Int) {
        if (items.isNotEmpty()) {
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
        }
    }

    fun removeItems() {
        items.clear()
        notifyDataSetChanged()
    }

    interface OnClickListener<in T> {

        fun onClick(position: Int, view: View, item: T)
    }
}