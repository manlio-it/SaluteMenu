package com.example.salutemendietamediterranea

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Pranzi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pranzi)

        val pulsante_indietro = findViewById<ImageButton>(R.id.indietro)
        pulsante_indietro.setOnClickListener {
            finish()
        }
    }
}