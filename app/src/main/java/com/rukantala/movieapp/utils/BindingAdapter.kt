package com.rukantala.movieapp.utils

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import coil.request.ImageRequest

@BindingAdapter("image")
fun bindImageBerita(iv: ImageView, url: String?) {
    url?.apply {
        val imageUrl = ImageRequest.Builder(iv.context)
            .data("http://image.tmdb.org/t/p/w500${this.toUri()}")
            .allowHardware(false)
            .target(iv)
            .build()
        iv.load("${imageUrl.data}") {
//            placeholder(R.drawable.placeholder_umkm)
        }
    }
}