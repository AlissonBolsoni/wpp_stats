package com.example.wppstats.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

object ImagemUtil {

    fun montaImagem(imageView: ImageView, link: String, context: Context) {
        if(link.startsWith("http"))
            Glide.with(context).load(link).into(imageView)
        else
            Glide.with(context).load(File(link)).into(imageView)
    }

}