package com.example.salutemendietamediterranea

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Fabbisogno_acqua : AppCompatActivity() {

    private var sesso: String = ""
    private var altezza: Double = 0.0
    private var peso: Double = 0.0
    private var eta: Int = 0
    private var attivita_fisica: String = ""

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fabbisogno_acqua)

        loadUserData()

        val pulsante_indietro = findViewById<ImageButton>(R.id.indietro)
        val informazionimancantiTextView: TextView = findViewById(R.id.informazionimancanti)
        val bottoneProfilo: Button = findViewById(R.id.bottone_profilo)
        val fabbisognoAcquaTextView: TextView = findViewById(R.id.fabbisogno_acqua)
        val fabbisogno_acqua_spiegazione1: TextView = findViewById(R.id.fabbisogno_acqua_spiegazione1)
        val fabbisogno_acqua_spiegazione2: TextView = findViewById(R.id.fabbisogno_acqua_spiegazione2)


        pulsante_indietro.setOnClickListener {
            finish()
        }

        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                loadUserData()
                verificaDati(informazionimancantiTextView, bottoneProfilo, fabbisognoAcquaTextView, fabbisogno_acqua_spiegazione1, fabbisogno_acqua_spiegazione2)
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }

    private fun loadUserData() {
        val sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        sesso = sharedPreferences.getString("sesso", "") ?: ""
        altezza = sharedPreferences.getFloat("altezza", 0.0f).toDouble()
        peso = sharedPreferences.getFloat("peso", 0.0f).toDouble()
        eta = sharedPreferences.getInt("eta", 0)
        attivita_fisica = sharedPreferences.getString("attivita_fisica", "") ?: ""
    }

    private fun verificaDati(informazionimancantiTextView: TextView, bottoneProfilo: Button, fabbisognoAcquaTextView: TextView, fabbisogno_acqua_spiegazione1 : TextView, fabbisogno_acqua_spiegazione2 : TextView) {
        if (altezza != 0.0 && peso != 0.0 && eta != 0) {
            informazionimancantiTextView.visibility = View.GONE
            bottoneProfilo.visibility = View.GONE

            fabbisognoAcquaTextView.visibility = View.VISIBLE
            fabbisogno_acqua_spiegazione1.visibility = View.VISIBLE
            fabbisogno_acqua_spiegazione2.visibility = View.VISIBLE

            val calcolaFabbisognoAcqua = calcolaFabbisognoAcqua()
            val fabbisognoAcquaArrotondato = (Math.round(calcolaFabbisognoAcqua / 0.25) * 0.25)

            fabbisognoAcquaTextView.text = "Fabbisogno d'acqua: %.2f L".format(fabbisognoAcquaArrotondato)
            fabbisogno_acqua_spiegazione1.text = "Il fabbisogno giornaliero di acqua stimato per te è di %.2f litri. Questo valore è calcolato in base alle informazioni presenti nel tuo profilo personale, come il sesso, il peso, l'età e il livello di attività fisica.".format(fabbisognoAcquaArrotondato)

        } else {
            informazionimancantiTextView.visibility = View.VISIBLE
            bottoneProfilo.visibility = View.VISIBLE

            fabbisognoAcquaTextView.visibility = View.GONE
            fabbisogno_acqua_spiegazione1.visibility = View.GONE
            fabbisogno_acqua_spiegazione2.visibility = View.GONE

            bottoneProfilo.setOnClickListener {
                val intent_profilo = Intent(this, Profilo_utente::class.java)
                startActivity(intent_profilo)
            }
        }
    }

    fun calcolaFabbisognoAcqua(): Double {
        val sessoNormalizzato = sesso.lowercase()
        val attivitaFisicaNormalizzata = attivita_fisica.lowercase()

        val livelloAttivita = when (attivitaFisicaNormalizzata) {
            "assente" -> 1.0
            "leggera" -> 1.1
            "moderata" -> 1.2
            "pesante" -> 1.3
            else -> 1.0
        }

        val baseAcqua = when (sessoNormalizzato) {
            "maschile" -> 0.028 * peso
            "femminile" -> 0.026 * peso
            else -> 0.026 * peso
        }

        val fabbisognoBase = baseAcqua * livelloAttivita

        val aggiustamentoEta = when {
            eta < 30 -> 0.0
            eta in 30..50 -> 0.05 * fabbisognoBase
            else -> 0.1 * fabbisognoBase
        }
        return fabbisognoBase + aggiustamentoEta
    }
}