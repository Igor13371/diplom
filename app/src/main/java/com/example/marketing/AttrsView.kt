package com.example.marketing

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter

object AttrsView {

    @JvmStatic
    @BindingAdapter(value = ["imgUrl", "scale"], requireAll = false)
    fun setImgUrl(view: AppCompatImageView, url: String?, scale: ScaleType? = null) {
        loadImage(view, R.drawable.pic_no_image, url, scale)
    }

}