package com.example.wppstats.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object ImagemUtil {

    fun montaImagem(imageView: ImageView, link: String, context: Context) {
        Glide.with(context).load(link).into(imageView)
    }

}