package com.example.wppstats.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File


object ImagemUtil {

    fun montaImagem(imageView: ImageView, link: String, context: Context) {
        if(link.startsWith("http"))
            Glide.with(context).load(link).into(imageView)
        else
            rotate(imageView, link, context)
    }

    fun rotate(imageView: ImageView, link: String, context: Context){
        var exifInterface: ExifInterface? = null
        try {
            exifInterface = ExifInterface(link)
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
        val orientation =
            exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
        val matrix = Matrix()

        when(orientation){
            ExifInterface.ORIENTATION_ROTATE_90 ->{
                matrix.setRotate(90F)
            }
            ExifInterface.ORIENTATION_ROTATE_180 ->{
                matrix.setRotate(180F)
            }
            ExifInterface.ORIENTATION_ROTATE_270 ->{
                matrix.setRotate(270F)
            }
            else ->{

            }
        }

        val image = File(link)
        val opt = BitmapFactory.Options()
        val bitmap = BitmapFactory.decodeFile(image.absolutePath, opt)

        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        Glide.with(context).load(rotatedBitmap).into(imageView)
    }

}