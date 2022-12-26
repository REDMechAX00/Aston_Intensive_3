package com.redmechax00.astonintensive3.utils

import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import com.squareup.picasso.Transformation
import kotlin.math.sqrt

class PicassoBitmapCompressor(private val quality: Int) : Transformation {
    override fun transform(source: Bitmap): Bitmap {

        val scaledBitmap = resizeBitmap(source).compress(quality)
        val bitmap = createBitmap(scaledBitmap.width, scaledBitmap.height, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        canvas.drawBitmap(scaledBitmap, 0f, 0f, null)

        source.recycle()

        return bitmap
    }

    override fun key(): String {
        return "bitmapCompressor"
    }

    private fun resizeBitmap(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val maxSize: Long = 2500 * 2500
        val currentSize: Long = (width.toLong() * height.toLong())

        if (currentSize < maxSize) return bitmap

        val scale: Float = maxSize.toFloat() / currentSize.toFloat()
        val newW = width * sqrt(scale)
        val newH = height * sqrt(scale)

        return Bitmap.createScaledBitmap(bitmap, newW.toInt(), newH.toInt(), true)
    }

}

