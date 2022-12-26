package com.redmechax00.astonintensive3.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executors

fun AppCompatActivity.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.isOnline(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            return true
        }
    }
    return false
}

fun EditText.onAfterTextChanged(onSuccess: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            onSuccess.invoke(editable.toString())
        }
    })
}

fun Bitmap.toByteArray(): ByteArray {
    val out = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 60, out)
    return out.toByteArray()
}

fun ByteArray.toBitmap(onSuccess: Bitmap.() -> Unit) {
    val executor = Executors.newSingleThreadExecutor()
    val handler = Handler(Looper.getMainLooper())

    executor.execute {
        val bitmap = BitmapFactory.decodeByteArray(this, 0, this.size)
        handler.post {
            onSuccess.invoke(bitmap)
        }
    }
}

fun Bitmap.compress(quality: Int): Bitmap {
    val out = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, quality, out)
    val bytes = out.toByteArray()
    val lowBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    return lowBitmap.copy(Bitmap.Config.RGB_565, true)
}


