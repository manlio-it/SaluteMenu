package com.example.salutemendietamediterranea

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.graphics.Paint
import android.widget.ImageButton
import android.widget.TextView
import kotlin.math.round
import java.util.Locale

class Fabbisogno_calorico : AppCompatActivity() {

    private var sesso: String = ""
    private var altezza: Double = 0.0
    private var peso: Double = 0.0
    private var eta: Int = 0
    private var attivita_fisica: String = ""

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fabbisogno_calorico)

        loadUserData()

        val fabbisognoCaloricoTextView: TextView = findViewById(R.id.fabbisogno_calorico)
        val informazionimancantiTextView: TextView = findViewById(R.id.informazionimancanti)
        val bottoneProfilo: Button = findViewById(R.id.bottone_profilo)
        val pulsante_indietro = findViewById<ImageButton>(R.id.indietro)
        val bmiTextView: TextView = findViewById(R.id.fabbisogno_calorico_tabella)
        val fabbisogno_calorico_spiegazione1: TextView = findViewById(R.id.fabbisogno_calorico_spiegazione1)
        val fabbisogno_calorico_spiegazione2: TextView = findViewById(R.id.fabbisogno_calorico_spiegazione2)
        val fabbisogno_calorico_spiegazione3: TextView = findViewById(R.id.fabbisogno_calorico_spiegazione3)
        val fabbisogno_calorico_spiegazione4: TextView = findViewById(R.id.fabbisogno_calorico_spiegazione4)
        val fabbisogno_calorico_spiegazione5: TextView = findViewById(R.id.fabbisogno_calorico_spiegazione5)
        val fabbisogno_calorico_tabella: TextView = findViewById(R.id.fabbisogno_calorico_tabella)


        pulsante_indietro.setOnClickListener {
            finish()
        }

        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                loadUserData()
                verificaDati(fabbisognoCaloricoTextView, informazionimancantiTextView, bottoneProfilo, bmiTextView, fabbisogno_calorico_spiegazione1, fabbisogno_calorico_spiegazione2,fabbisogno_calorico_spiegazione3, fabbisogno_calorico_spiegazione4, fabbisogno_calorico_spiegazione5, fabbisogno_calorico_tabella)
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

    private fun verificaDati(fabbisognoCaloricoTextView: TextView, informazionimancantiTextView: TextView, bottoneProfilo: Button, bmiTextView: TextView, fabbisogno_calorico_spiegazione1 : TextView,fabbisogno_calorico_spiegazione2 : TextView, fabbisogno_calorico_spiegazione3 : TextView, fabbisogno_calorico_spiegazione4 : TextView, fabbisogno_calorico_spiegazione5 : TextView, fabbisogno_calorico_tabella : TextView) {
        if (altezza != 0.0 && peso != 0.0 && eta != 0) {
            informazionimancantiTextView.visibility = View.GONE
            bottoneProfilo.visibility = View.GONE

            fabbisognoCaloricoTextView.visibility = View.VISIBLE
            fabbisognoCaloricoTextView.visibility = View.VISIBLE
            bmiTextView.visibility = View.VISIBLE
            fabbisogno_calorico_spiegazione1.visibility = View.VISIBLE
            fabbisogno_calorico_spiegazione2.visibility = View.VISIBLE
            fabbisogno_calorico_spiegazione3.visibility = View.VISIBLE
            fabbisogno_calorico_spiegazione4.visibility = View.VISIBLE
            fabbisogno_calorico_spiegazione5.visibility = View.VISIBLE
            fabbisogno_calorico_tabella.visibility = View.VISIBLE

            val bmi = peso / (altezza/100 * altezza/100)
            val fabbisognoCalorico = calcolaFabbisognoCalorico(sesso, peso, altezza, eta, attivita_fisica)
            val fabbisognoCaloricoArrotondato = (round(fabbisognoCalorico / 10) * 10).toInt()
            fabbisognoCaloricoTextView.text = "BMI: %.2f\nFabbisogno Calorico: %d kcal".format(bmi, fabbisognoCaloricoArrotondato)

            var stato = ""
            stato = RangeBMI(bmi)

            var x = fabbisognoCaloricoArrotondato*0.8
            var y = fabbisognoCaloricoArrotondato*1.2
            var z = fabbisognoCaloricoArrotondato

            val xArrotondato = (round(x / 10) * 10).toInt()
            val yArrotondato = (round(y / 10) * 10).toInt()

            fabbisogno_calorico_spiegazione4.text = "Quindi riassumendo, considerando il tuo fabbisogno calorico di partenza:\nPer dimagrire dovrai consumare: %d kcal in media al giorno.\nPer prendere peso dovrai consumare: %d kcal in media al giorno.\nPer mantenre il tuo peso dovrai consumare %d kcal in media al giorno.".format(xArrotondato,yArrotondato,z)
            fabbisogno_calorico_spiegazione2.text = "In base alle informazioni inserite nel tuo profilo utente, il tuo BMI risulta essere di %.2f e viene classificato come %s.".format(bmi, stato)

            bmiTextView.paintFlags = bmiTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            bmiTextView.setTypeface(bmiTextView.typeface, android.graphics.Typeface.BOLD)

            bmiTextView.setOnClickListener {
                val intent = Intent(this, Guida_Fabbisogno_BMI::class.java)
                startActivity(intent)
            }

        } else {
            informazionimancantiTextView.visibility = View.VISIBLE
            bottoneProfilo.visibility = View.VISIBLE

            fabbisognoCaloricoTextView.visibility = View.GONE
            fabbisognoCaloricoTextView.visibility = View.GONE
            bmiTextView.visibility = View.GONE
            fabbisogno_calorico_spiegazione1.visibility = View.GONE
            fabbisogno_calorico_spiegazione2.visibility = View.GONE
            fabbisogno_calorico_spiegazione3.visibility = View.GONE
            fabbisogno_calorico_spiegazione4.visibility = View.GONE
            fabbisogno_calorico_spiegazione5.visibility = View.GONE
            fabbisogno_calorico_tabella.visibility = View.GONE

            bottoneProfilo.setOnClickListener {
                val intent_profilo = Intent(this, Profilo_utente::class.java)
                startActivity(intent_profilo)
            }
        }
    }

    private fun calcolaFabbisognoCalorico(sesso: String, peso: Double, altezza: Double, eta: Int, attivita_fisica: String): Double {
        val bmr = if (sesso.lowercase(Locale.ROOT) == "maschio") {
            88.362 + (13.397 * peso) + (4.799 * altezza) - (5.677 * eta)
        } else {
            447.593 + (9.247 * peso) + (3.098 * altezza) - (4.330 * eta)
        }

        val fattoreAttivita = when (attivita_fisica.lowercase(Locale.ROOT)) {
            "assente" -> 1.2
            "leggera" -> 1.375
            "moderata" -> 1.55
            "pesante" -> 1.725
            else -> 1.0
        }
        return bmr * fattoreAttivita
    }

    private fun RangeBMI (bmi : Double): String{
        var stato = ""

        if (sesso.lowercase(Locale.ROOT) == "maschio") {
            when {
                eta <= 24 -> {
                    stato = when {
                        bmi < 19 -> "sottopeso"
                        bmi >= 19 && bmi < 24 -> "peso ottimale"
                        bmi >= 24 && bmi < 29 -> "sovrappeso"
                        bmi >= 29 && bmi < 34 -> "obesità lieve"
                        bmi >= 34 && bmi < 39 -> "obesità"
                        bmi >= 39 -> "obesità grave"
                        else -> "valore non valido"
                    }
                }
                eta in 25..34 -> {
                    stato = when {
                        bmi < 20 -> "sottopeso"
                        bmi >= 20 && bmi < 25 -> "peso ottimale"
                        bmi >= 25 && bmi < 30 -> "sovrappeso"
                        bmi >= 30 && bmi < 35 -> "obesità lieve"
                        bmi >= 35 && bmi < 40 -> "obesità"
                        bmi >= 40 -> "obesità grave"
                        else -> "valore non valido"
                    }
                }
                eta in 35..44 -> {
                    stato = when {
                        bmi < 21 -> "sottopeso"
                        bmi >= 21 && bmi < 26 -> "peso ottimale"
                        bmi >= 26 && bmi < 31 -> "sovrappeso"
                        bmi >= 31 && bmi < 36 -> "obesità lieve"
                        bmi >= 36 && bmi < 41 -> "obesità"
                        bmi >= 41 -> "obesità grave"
                        else -> "valore non valido"
                    }
                }
                eta in 45..54 -> {
                    stato = when {
                        bmi < 22 -> "sottopeso"
                        bmi >= 22 && bmi < 27 -> "peso ottimale"
                        bmi >= 27 && bmi < 32 -> "sovrappeso"
                        bmi >= 32 && bmi < 37 -> "obesità lieve"
                        bmi >= 37 && bmi < 42 -> "obesità"
                        bmi >= 42 -> "obesità grave"
                        else -> "valore non valido"
                    }
                }
                eta in 55..64 -> {
                    stato = when {
                        bmi < 23 -> "sottopeso"
                        bmi >= 23 && bmi < 28 -> "peso ottimale"
                        bmi >= 28 && bmi < 33 -> "sovrappeso"
                        bmi >= 33 && bmi < 38 -> "obesità lieve"
                        bmi >= 38 && bmi < 43 -> "obesità"
                        bmi >= 43 -> "obesità grave"
                        else -> "valore non valido"
                    }
                }
                eta >= 65 -> {
                    stato = when {
                        bmi < 24 -> "sottopeso"
                        bmi >= 24 && bmi < 29 -> "peso ottimale"
                        bmi >= 29 && bmi < 34 -> "sovrappeso"
                        bmi >= 34 && bmi < 39 -> "obesità lieve"
                        bmi >= 39 && bmi < 44 -> "obesità"
                        bmi >= 44 -> "obesità grave"
                        else -> "valore non valido"
                    }
                }
                else -> stato = "età non valida"
            }
        } else if (sesso.lowercase(Locale.ROOT) == "femmina") {
            when {
                eta <= 24 -> {
                    stato = when {
                        bmi < 18 -> "sottopeso"
                        bmi >= 18 && bmi < 23 -> "peso ottimale"
                        bmi >= 23 && bmi < 28 -> "sovrappeso"
                        bmi >= 28 && bmi < 33 -> "obesità lieve"
                        bmi >= 33 && bmi < 38 -> "obesità"
                        bmi >= 38 -> "obesità grave"
                        else -> "valore non valido"
                    }
                }
                eta in 25..34 -> {
                    stato = when {
                        bmi < 19 -> "sottopeso"
                        bmi >= 19 && bmi < 24 -> "peso ottimale"
                        bmi >= 24 && bmi < 29 -> "sovrappeso"
                        bmi >= 29 && bmi < 34 -> "obesità lieve"
                        bmi >= 34 && bmi < 39 -> "obesità"
                        bmi >= 39 -> "obesità grave"
                        else -> "valore non valido"
                    }
                }
                eta in 35..44 -> {
                    stato = when {
                        bmi < 20 -> "sottopeso"
                        bmi >= 20 && bmi < 25 -> "peso ottimale"
                        bmi >= 25 && bmi < 30 -> "sovrappeso"
                        bmi >= 30 && bmi < 35 -> "obesità lieve"
                        bmi >= 35 && bmi < 40 -> "obesità"
                        bmi >= 40 -> "obesità grave"
                        else -> "valore non valido"
                    }
                }
                eta in 45..54 -> {
                    stato = when {
                        bmi < 21 -> "sottopeso"
                        bmi >= 21 && bmi < 26 -> "peso ottimale"
                        bmi >= 26 && bmi < 31 -> "sovrappeso"
                        bmi >= 31 && bmi < 36 -> "obesità lieve"
                        bmi >= 36 && bmi < 41 -> "obesità"
                        bmi >= 41 -> "obesità grave"
                        else -> "valore non valido"
                    }
                }
                eta in 55..64 -> {
                    stato = when {
                        bmi < 22 -> "sottopeso"
                        bmi >= 22 && bmi < 27 -> "peso ottimale"
                        bmi >= 27 && bmi < 32 -> "sovrappeso"
                        bmi >= 32 && bmi < 37 -> "obesità lieve"
                        bmi >= 37 && bmi < 42 -> "obesità"
                        bmi >= 42 -> "obesità grave"
                        else -> "valore non valido"
                    }
                }
                eta >= 65 -> {
                    stato = when {
                        bmi < 23 -> "sottopeso"
                        bmi >= 23 && bmi < 28 -> "peso ottimale"
                        bmi >= 28 && bmi < 33 -> "sovrappeso"
                        bmi >= 33 && bmi < 38 -> "obesità lieve"
                        bmi >= 38 && bmi < 43 -> "obesità"
                        bmi >= 43 -> "obesità grave"
                        else -> "valore non valido"
                    }
                }
                else -> stato = "età non valida"
            }
        } else {
            stato = "sesso non valido"
        }
        return stato
    }
}
