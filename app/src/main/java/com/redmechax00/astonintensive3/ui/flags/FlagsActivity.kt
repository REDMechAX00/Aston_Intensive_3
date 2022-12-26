package com.redmechax00.astonintensive3.ui.flags

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.redmechax00.astonintensive3.R

class FlagsActivity : AppCompatActivity(R.layout.activity_flags) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.color_primary_variant_flags)
    }
}