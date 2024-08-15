package com.example.salutemendietamediterranea

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SaluteMenuPrincipale : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.salutemenu_principale)

        val pulsante_utente = findViewById<Button>(R.id.bottone_utente)
        pulsante_utente.setOnClickListener {
            val intent_utente = Intent(this, Profilo_utente::class.java)
            startActivity(intent_utente)
        }

        val pulsante_calorie = findViewById<Button>(R.id.bottone_calorie)
        pulsante_calorie.setOnClickListener {
            val intent_calorie = Intent(this, Fabbisogno_calorico::class.java)
            startActivity(intent_calorie)
        }

        val pulsante_acqua = findViewById<Button>(R.id.bottone_acqua)
        pulsante_acqua.setOnClickListener {
            val intent_acqua = Intent(this, Fabbisogno_acqua::class.java)
            startActivity(intent_acqua)
        }

        val pulsante_notifiche = findViewById<Button>(R.id.bottone_notifiche)
        pulsante_notifiche.setOnClickListener {
            val intent_notifiche = Intent(this, Gestione_notifiche::class.java)
            startActivity(intent_notifiche)
        }

        val pulsante_superfood = findViewById<Button>(R.id.bottone_food)
        pulsante_superfood.setOnClickListener {
            val intent_superfood = Intent(this, Super_Food::class.java)
            startActivity(intent_superfood)
        }

        val pulsante_colazione = findViewById<Button>(R.id.bottone_colazioni)
        pulsante_colazione.setOnClickListener {
            val intent_colazione = Intent(this, Colazioni::class.java)
            startActivity(intent_colazione)
        }

        val pulsante_pranzo = findViewById<Button>(R.id.bottone_pranzi)
        pulsante_pranzo.setOnClickListener {
            val intent_pranzo = Intent(this, Pranzi::class.java)
            startActivity(intent_pranzo)
        }

        val pulsante_cena = findViewById<Button>(R.id.bottone_cene)
        pulsante_cena.setOnClickListener {
            val intent_cena = Intent(this, Cene::class.java)
            startActivity(intent_cena)
        }
    }
}