package com.example.salutemendietamediterranea

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import android.graphics.drawable.Drawable
import android.widget.ImageView

class Guida_Fabbisogno_BMI : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guida_fabbisogno_bmi)

        val pulsante_indietro = findViewById<ImageButton>(R.id.indietro)
        pulsante_indietro.setOnClickListener {
            finish()
        }

        val imageView: ImageView = findViewById(R.id.imagetabella)
        val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.tabellla_bmi)
        imageView.doOnLayout {
            drawable?.let {
                val imageWidth = it.intrinsicWidth
                val imageHeight = it.intrinsicHeight
                val imageViewWidth = imageView.width

                val aspectRatio = imageWidth.toFloat() / imageHeight.toFloat()
                val imageViewHeight = (imageViewWidth / aspectRatio).toInt()

                val layoutParams = imageView.layoutParams
                layoutParams.height = imageViewHeight
                imageView.layoutParams = layoutParams
                }
            }
    }
}