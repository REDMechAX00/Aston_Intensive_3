package com.redmechax00.astonintensive3.ui.load_images

import android.widget.ImageView
import com.redmechax00.astonintensive3.utils.PicassoBitmapCompressor
import com.squareup.picasso.Picasso

class ImagesPicassoActivity : BaseImagesActivity() {
    override fun loadAndSetImage(strUrl: String, img: ImageView, bytes: ByteArray?.() -> Unit) {
        super.loadAndSetImage(strUrl, img, bytes)
        Picasso
            .get()
            .load(strUrl)
            .transform(PicassoBitmapCompressor(70))
            .into(img)
    }
}