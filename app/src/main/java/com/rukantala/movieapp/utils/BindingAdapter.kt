package com.rukantala.movieapp.utils

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import coil.request.ImageRequest

@BindingAdapter("image")
fun bindImageMovie(iv: ImageView, url: String?) {
    url?.apply {
        val imageUrl = ImageRequest.Builder(iv.context)
            .data("https://image.tmdb.org/t/p/w500${this.toUri()}")
            .allowHardware(false)
            .target(iv)
            .build()
        iv.load("${imageUrl.data}") {
//            placeholder(R.drawable.placeholder_umkm)
        }
    }
}

@BindingAdapter("imageAvatar")
fun bindImageAvatar(iv: ImageView, url: String?) {
    url?.apply {
        Log.v("DATA", url.toString())
        val imageUrl = ImageRequest.Builder(iv.context)
            .data(this.toUri())
            .allowHardware(false)
            .target(iv)
            .build()
        iv.load(imageUrl.data.toString().toUri()) {
//            placeholder(R.drawable.placeholder_umkm)
        }
    }
}