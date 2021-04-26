package com.example.marketing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Priority
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import java.security.MessageDigest

const val GLIDE_DISK_CACHE_SIZE = (250 * 1024 * 1024).toLong()
const val GLIDE_MEMORY_CACHE_SIZE = (50 * 1024 * 1024).toLong()

enum class ScaleType {
    FIT_CENTER,
    CENTER_CROP,
    CENTER_INSIDE
}

@GlideModule
class GlideAppModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.apply {
            setDefaultRequestOptions(
                RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
                    .downsample(DownsampleStrategy.CENTER_OUTSIDE)
                    .encodeFormat(Bitmap.CompressFormat.JPEG)
                    .dontAnimate()
                    .dontTransform()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
            setDiskCache(InternalCacheDiskCacheFactory(context, GLIDE_DISK_CACHE_SIZE))
            setMemoryCache(LruResourceCache(GLIDE_MEMORY_CACHE_SIZE))
        }
        super.applyOptions(context, builder)
    }

    override fun isManifestParsingEnabled(): Boolean = false
}

class StringSignature(val url: String?) : Key {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        url?.let { messageDigest.update(it.toByteArray(Charsets.UTF_8)) }
    }
}

fun loadImage(
    view: ImageView,
    stub: Int?,
    imgUrl: String?,
    scaleType: ScaleType?
) {

    val stubRes = stub ?: R.drawable.pic_no_image

    if (imgUrl.isNullOrEmpty()) {
        view.setImageResource(stubRes)
        return
    }

    fun load(
        width: Int = Target.SIZE_ORIGINAL,
        height: Int = Target.SIZE_ORIGINAL
    ) {
        GlideApp.with(view.context)
            .load(imgUrl)
            .error(stubRes)
            .placeholder(stubRes)
            .priority(Priority.HIGH)
            .thumbnail(0.5f)
            .signature(StringSignature(imgUrl))
            .apply {
                when(scaleType) {
                    ScaleType.FIT_CENTER -> fitCenter()
                    ScaleType.CENTER_CROP -> centerCrop()
                    ScaleType.CENTER_INSIDE -> centerInside()
                }
            }
            .into(object : CustomTarget<Drawable>(width, height) {
                override fun onLoadCleared(placeholder: Drawable?) {
                    view.setImageDrawable(placeholder)
                }

                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    view.setImageDrawable(resource)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    view.setImageResource(stubRes)
                }
            })
    }

    load()
    Glide.get(view.context).clearMemory();
}