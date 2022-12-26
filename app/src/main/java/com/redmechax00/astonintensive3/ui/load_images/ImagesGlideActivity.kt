package com.redmechax00.astonintensive3.ui.load_images

import android.widget.ImageView
import com.bumptech.glide.Glide

class ImagesGlideActivity : BaseImagesActivity() {
    override fun loadAndSetImage(strUrl: String, img: ImageView, bytes: ByteArray?.() -> Unit) {
        super.loadAndSetImage(strUrl, img, bytes)
        Glide
            .with(this)
            .load(strUrl)
            .fitCenter()
            .into(img)
    }
}