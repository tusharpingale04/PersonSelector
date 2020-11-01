package com.tushar.personselector.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import com.tushar.personselector.R

/**
 * Binding adapter to load images
 */
@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView,url: String?) {
    url?.let {
        imageView.load(it) {
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }
    } ?: imageView.setImageResource(R.drawable.ic_broken_image)
}