package com.github.candalo.yarc.presentation.extensions

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

internal fun AppCompatImageView.loadImage(context: Context, imageUrl: String?) {
    Glide
        .with(context)
        .load(imageUrl)
        .into(this)
}