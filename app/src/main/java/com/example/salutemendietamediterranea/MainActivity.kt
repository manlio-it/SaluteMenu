package com.example.salutemendietamediterranea

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pulsante_introduzione_dieta_mediteranea = findViewById<Button>(R.id.introButton)
        pulsante_introduzione_dieta_mediteranea.setOnClickListener()
        {
            val intent_introduzione = Intent(this, Scegliere_Mediterranea::class.java)
            startActivity(intent_introduzione)
        }

        val into_saluteMenu = findViewById<Button>(R.id.SaluteMenuButton)
        into_saluteMenu.setOnClickListener()
        {
            val intent_saluteMenu = Intent(this, SaluteMenuPrincipale::class.java)
            startActivity(intent_saluteMenu)
        }
    }
}