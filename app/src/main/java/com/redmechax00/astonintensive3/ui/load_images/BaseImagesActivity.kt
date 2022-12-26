package com.redmechax00.astonintensive3.ui.load_images

import android.os.Bundle
import android.webkit.URLUtil
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.redmechax00.astonintensive3.R
import com.redmechax00.astonintensive3.databinding.ActivityImagesBaseBinding
import com.redmechax00.astonintensive3.utils.isOnline
import com.redmechax00.astonintensive3.utils.onAfterTextChanged
import com.redmechax00.astonintensive3.utils.showToast
import com.redmechax00.astonintensive3.utils.toBitmap

open class BaseImagesActivity : AppCompatActivity() {

    companion object {
        const val KEY_BITMAP_ON_BYTES = "bitmap_on_bytes"
        const val KEY_LAST_INPUT = "last_input"
    }

    private lateinit var binding: ActivityImagesBaseBinding
    private lateinit var edText: EditText
    private lateinit var imageView: ImageView

    private var lastInput: String? = null

    //If we need to save bitmap
    private var bitmapOnBytes: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagesBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        edText = binding.imagesEdtext
        imageView = binding.imagesImageView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //All
        outState.putString(KEY_LAST_INPUT, lastInput)
        //Only for No Libraries
        outState.putByteArray(KEY_BITMAP_ON_BYTES, bitmapOnBytes)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //All
        lastInput = savedInstanceState.getString(KEY_LAST_INPUT)
        //Only for No Libraries
        bitmapOnBytes = savedInstanceState.getByteArray(KEY_BITMAP_ON_BYTES)
    }

    override fun onResume() {
        super.onResume()
        restoreView()
        addEdTextListener()
    }

    private fun restoreView() {
        //If we have saved input then load with it (from cache for Glide, Picasso)
        if (bitmapOnBytes == null && lastInput != null) {
            loadAndSetImage(lastInput!!, imageView)
        }
        //If we have saved bitmap then set it to imageView without load
        if (bitmapOnBytes != null) {
            bitmapOnBytes!!.toBitmap {
                imageView.setImageBitmap(this)
            }
        }
    }

    private fun addEdTextListener() {
        edText.onAfterTextChanged {
            if (canWeStartLoadImage(it)) {
                lastInput = it
                loadAndSetImage(it, imageView) { bitmapOnBytes = this }
            }
        }
    }

    private fun canWeStartLoadImage(input: String): Boolean {
        //Don't repeat downloading when we have same input
        if (lastInput == input) return false

        //Check internet connection
        if (!isOnline()) {
            showToast(getString(R.string.error_no_internet))
            return false
        }

        //Check URL is valid
        if (!URLUtil.isValidUrl(input)) {
            showToast(getString(R.string.error_invalid_url))
            return false
        }

        return true
    }

    open fun loadAndSetImage(strUrl: String, img: ImageView, bytes: ByteArray?.() -> Unit = {}) {}

}