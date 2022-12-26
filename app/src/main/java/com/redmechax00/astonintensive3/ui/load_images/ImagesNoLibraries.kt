package com.redmechax00.astonintensive3.ui.load_images

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.redmechax00.astonintensive3.R
import com.redmechax00.astonintensive3.utils.showToast
import com.redmechax00.astonintensive3.utils.toByteArray
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class ImagesNoLibraries : BaseImagesActivity() {

    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    override fun loadAndSetImage(strUrl: String, img: ImageView, bytes: ByteArray?.() -> Unit) {
        super.loadAndSetImage(strUrl, img, bytes)
        executor.execute {
            val bitmap = loadImage(strUrl)?.copy(Bitmap.Config.RGB_565, false)
            val bitmapOnBytes = bitmap?.toByteArray()
            handler.post {
                if (bitmap == null) showToast(getString(R.string.error_image_not_found))
                else {
                    img.setImageBitmap(bitmap)
                    bytes.invoke(bitmapOnBytes)
                }
            }
        }
    }

    private fun loadImage(textUrl: String): Bitmap? {
        try {
            val con = URL(textUrl).openConnection() as HttpURLConnection
            con.connect()
            return BitmapFactory.decodeStream(con.inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

}

