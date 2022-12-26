package com.redmechax00.astonintensive3.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.redmechax00.astonintensive3.R
import com.redmechax00.astonintensive3.databinding.ActivityMainBinding
import com.redmechax00.astonintensive3.ui.flags.FlagsActivity
import com.redmechax00.astonintensive3.ui.load_images.ImagesGlideActivity
import com.redmechax00.astonintensive3.ui.load_images.ImagesNoLibraries
import com.redmechax00.astonintensive3.ui.load_images.ImagesPicassoActivity

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listOfButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        listOfButtons = listOf(
            binding.mainBtnFlags,
            binding.mainBtnGlide,
            binding.mainBtnPicasso,
            binding.mainBtnNoLibraries
        )
    }

    override fun onResume() {
        super.onResume()
        setOnClickListeners()
    }

    override fun onPause() {
        super.onPause()
        removeOnClickListeners()
    }

    private fun setOnClickListeners() {
        listOfButtons.forEach { it.setOnClickListener(this) }
    }

    private fun removeOnClickListeners() {
        listOfButtons.forEach { it.setOnClickListener(null) }
    }

    override fun onClick(v: View?) {
        (v as Button).launchActivity()
    }

    private fun Button.launchActivity() {
        launchActivity(findActivityByTitle(this.text.toString()))
    }

    private fun launchActivity(activity: AppCompatActivity?) {
        activity?.let {
            val intent = Intent(this, activity::class.java)
            startActivity(intent)
        }
    }

    private fun findActivityByTitle(title: String): AppCompatActivity? =
        when (title) {
            getString(R.string.activity_flags_title) -> FlagsActivity()
            getString(R.string.activity_glide_title) -> ImagesGlideActivity()
            getString(R.string.activity_picasso_title) -> ImagesPicassoActivity()
            getString(R.string.activity_no_libraries_title) -> ImagesNoLibraries()
            else -> null
        }
}