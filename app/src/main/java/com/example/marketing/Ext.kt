package com.example.teacherhelper.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

    val Context.layoutInflater: LayoutInflater
        get() = LayoutInflater.from(this)
    fun Context.inflate(@LayoutRes resource: Int, root: ViewGroup, attachToRoot: Boolean): View {
        return layoutInflater.inflate(resource, root, attachToRoot)
    }
