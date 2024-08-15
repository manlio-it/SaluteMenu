package com.example.salutemendietamediterranea

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity

class Profilo_utente : AppCompatActivity() {

    var sesso: String = ""
    var altezza: Double = 0.0
    var peso: Double = 0.0
    var eta: Int = 0
    var stop: Boolean = false
    var attivita_fisica: String = ""

    fun selezione_sesso_maschio() {
        val bottone_maschio_selezione: Button = findViewById(R.id.bottone_maschio)
        val bottone_femmina_selezione: Button = findViewById(R.id.bottone_femmina)
        bottone_maschio_selezione.setBackgroundResource(R.drawable.selezione_nero_on)
        bottone_femmina_selezione.setBackgroundResource(R.drawable.selezione_nero_off)
        bottone_maschio_selezione.setTextColor(resources.getColor(R.color.ArancioneSaluteMenu))
        bottone_femmina_selezione.setTextColor(resources.getColor(R.color.white))
        sesso = "maschio"
    }
    fun selezione_sesso_femmina() {
        val bottone_maschio_selezione: Button = findViewById(R.id.bottone_maschio)
        val bottone_femmina_selezione: Button = findViewById(R.id.bottone_femmina)
        bottone_maschio_selezione.setBackgroundResource(R.drawable.selezione_nero_off)
        bottone_femmina_selezione.setBackgroundResource(R.drawable.selezione_nero_on)
        bottone_maschio_selezione.setTextColor(resources.getColor(R.color.white))
        bottone_femmina_selezione.setTextColor(resources.getColor(R.color.ArancioneSaluteMenu))
        sesso = "femmina"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profilo_utente)

        val pulsante_indietro = findViewById<ImageButton>(R.id.indietro)
        pulsante_indietro.setOnClickListener {
            finish()
        }

        val pulsante_salva = findViewById<Button>(R.id.bottone_salva)
        pulsante_salva.setOnClickListener {
            if ((sesso == "femmina" || sesso == "maschio") && altezza != 0.0 && peso != 0.0 && eta != 0 && attivita_fisica.isNotEmpty()) {
                saveUserData()
                Toast.makeText(this, "Dati salvati con successo!", Toast.LENGTH_SHORT).show()
                val scrollView = findViewById<ScrollView>(R.id.scrollView)
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
            } else {
                Toast.makeText(this, "Per favore, completa tutti i campi", Toast.LENGTH_SHORT).show()
            }
        }

        val pulsante_elimina = findViewById<Button>(R.id.bottone_elimina)
        pulsante_elimina.setOnClickListener {

            val sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            sesso = ""
            altezza = 0.0
            peso = 0.0
            eta = 0
            attivita_fisica = ""

            val bottone_maschio_selezione: Button = findViewById(R.id.bottone_maschio)
            val bottone_femmina_selezione: Button = findViewById(R.id.bottone_femmina)
            bottone_maschio_selezione.setBackgroundResource(R.drawable.selezione_nero_off)
            bottone_femmina_selezione.setBackgroundResource(R.drawable.selezione_nero_off)
            bottone_maschio_selezione.setTextColor(resources.getColor(R.color.white))
            bottone_femmina_selezione.setTextColor(resources.getColor(R.color.white))

            val la_tua_altezza: TextView = findViewById(R.id.editTextNumber)
            la_tua_altezza.text = "Clicca per inserire la tua altezza..."

            val il_tuo_peso: TextView = findViewById(R.id.editTextNumber2)
            il_tuo_peso.text = "Clicca per inserire il tuo peso..."

            val la_tua_eta: TextView = findViewById(R.id.editTextNumber3)
            la_tua_eta.text = "Clicca per inserire la tua età..."

            val pulsante_attivita_fisica: TextView = findViewById(R.id.editTextNumber21)
            pulsante_attivita_fisica.text = "Clicca per inserire l'attività fisica..."

            Toast.makeText(this, "Dati eliminati con successo!", Toast.LENGTH_SHORT).show()
        }

        val pulsante_maschio = findViewById<Button>(R.id.bottone_maschio)
        pulsante_maschio.setOnClickListener {
            if (!stop) {
                selezione_sesso_maschio()
            }
        }

        val pulsante_femmina = findViewById<Button>(R.id.bottone_femmina)
        pulsante_femmina.setOnClickListener {
            if (!stop) {
                selezione_sesso_femmina()
            }
        }

        val pulsante_altezza: TextView = findViewById(R.id.editTextNumber)
        pulsante_altezza.setOnClickListener {
            if (!stop) {
                val dialog = Dialog(this)
                val inflater: LayoutInflater = layoutInflater
                val dialogLayout: View = inflater.inflate(R.layout.dialog_template, null)
                dialog.setContentView(dialogLayout)
                val testo_altezza: EditText = dialogLayout.findViewById(R.id.Edit_text_layout_input)
                val testo: TextView = dialogLayout.findViewById(R.id.textView_input_testo)
                testo.setText("Inserisci la tua altezza in centimetri (cm)")
                dialog.show()
                val button_ok: Button = dialogLayout.findViewById(R.id.button_ok)
                val la_tua_altezza: TextView = findViewById(R.id.editTextNumber)
                val button_annulla: Button = dialogLayout.findViewById(R.id.button_annulla)
                button_ok.setOnClickListener {
                    la_tua_altezza.text = "${testo_altezza.text} cm"
                    altezza = testo_altezza.text.toString().toDouble()
                    dialog.dismiss()
                }
                button_annulla.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }

        val pulsante_peso: TextView = findViewById(R.id.editTextNumber2)
        pulsante_peso.setOnClickListener {
            if (!stop) {
                val dialog = Dialog(this)
                val inflater: LayoutInflater = layoutInflater
                val dialogLayout: View = inflater.inflate(R.layout.dialog_template, null)
                dialog.setContentView(dialogLayout)
                val testo_peso: EditText = dialogLayout.findViewById(R.id.Edit_text_layout_input)
                val testo: TextView = dialogLayout.findViewById(R.id.textView_input_testo)
                testo.setText("Inserisci il tuo peso (kg)")
                dialog.show()
                val button_ok: Button = dialogLayout.findViewById(R.id.button_ok)
                val il_tuo_peso: TextView = findViewById(R.id.editTextNumber2)
                val button_annulla: Button = dialogLayout.findViewById(R.id.button_annulla)
                button_ok.setOnClickListener {
                    il_tuo_peso.text = "${testo_peso.text} kg"
                    peso = testo_peso.text.toString().toDouble()
                    dialog.dismiss()
                }
                button_annulla.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }

        val pulsante_eta: TextView = findViewById(R.id.editTextNumber3)
        pulsante_eta.setOnClickListener {
            if (!stop) {
                val dialog = Dialog(this)
                val inflater: LayoutInflater = layoutInflater
                val dialogLayout: View = inflater.inflate(R.layout.dialog_template, null)
                dialog.setContentView(dialogLayout)
                val testo_eta: EditText = dialogLayout.findViewById(R.id.Edit_text_layout_input)
                val testo: TextView = dialogLayout.findViewById(R.id.textView_input_testo)
                testo.setText("Inserisci la tua età (anni)")
                dialog.show()
                val button_ok: Button = dialogLayout.findViewById(R.id.button_ok)
                val la_tua_eta: TextView = findViewById(R.id.editTextNumber3)
                val button_annulla: Button = dialogLayout.findViewById(R.id.button_annulla)
                button_ok.setOnClickListener {
                    la_tua_eta.text = "${testo_eta.text} anni"
                    eta = testo_eta.text.toString().toInt()
                    dialog.dismiss()
                }
                button_annulla.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }

        val pulsante_attivita_fisica: TextView = findViewById(R.id.editTextNumber21)
        pulsante_attivita_fisica.setOnClickListener {
            if (!stop) {
                val dialog_attivita_fisica = Dialog(this)
                val inflater_attivita_fisica: LayoutInflater = layoutInflater
                val dialogLayout_attivita_fisica: View = inflater_attivita_fisica.inflate(
                    R.layout.dialog_scrollview_attivita_fisica, null
                )
                dialog_attivita_fisica.setContentView(dialogLayout_attivita_fisica)
                dialog_attivita_fisica_click(dialogLayout_attivita_fisica)
                dialog_attivita_fisica.show()
                val button_ok_attivita_fisica: Button = dialogLayout_attivita_fisica.findViewById(R.id.button_ok)
                val button_annulla_attivita_fisica: Button = dialogLayout_attivita_fisica.findViewById(R.id.button_annulla)
                button_ok_attivita_fisica.setOnClickListener {
                    pulsante_attivita_fisica.text = attivita_fisica
                    dialog_attivita_fisica.dismiss()
                }
                button_annulla_attivita_fisica.setOnClickListener {
                    dialog_attivita_fisica.dismiss()
                }
            }
        }
        loadUserData()
    }

    private fun saveUserData() {
        val sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("sesso", sesso)
        editor.putFloat("altezza", altezza.toFloat())
        editor.putFloat("peso", peso.toFloat())
        editor.putInt("eta", eta)
        editor.putString("attivita_fisica", attivita_fisica)
        editor.apply()
    }
    private fun loadUserData() {
        val sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        sesso = sharedPreferences.getString("sesso", "") ?: ""
        altezza = sharedPreferences.getFloat("altezza", 0.0f).toDouble()
        peso = sharedPreferences.getFloat("peso", 0.0f).toDouble()
        eta = sharedPreferences.getInt("eta", 0)
        attivita_fisica = sharedPreferences.getString("attivita_fisica", "") ?: ""

        if (sesso == "maschio") {
            selezione_sesso_maschio()
        } else if (sesso == "femmina") {
            selezione_sesso_femmina()
        }

        if (altezza != 0.0) {
            val la_tua_altezza: TextView = findViewById(R.id.editTextNumber)
            la_tua_altezza.text = "${altezza.toInt()} cm"
        }

        if (peso != 0.0) {
            val il_tuo_peso: TextView = findViewById(R.id.editTextNumber2)
            il_tuo_peso.text = "${peso.toInt()} kg"
        }

        if (eta != 0) {
            val la_tua_eta: TextView = findViewById(R.id.editTextNumber3)
            la_tua_eta.text = "${eta} anni"
        }

        if (attivita_fisica.isNotEmpty()) {
            val pulsante_attivita_fisica: TextView = findViewById(R.id.editTextNumber21)
            pulsante_attivita_fisica.text = attivita_fisica
        }
    }

    fun dialog_attivita_fisica_click(dialogLayout: View) {
        val size_scelto: Float = 20.0F

        val pulsante_assente: TextView = dialogLayout.findViewById(R.id.textView_assente)
        pulsante_assente.setOnClickListener {
            pulisci_scritte_attivita_fisica(dialogLayout)
            pulsante_assente.setTextColor(getColor(R.color.VerdeBordoSaluteMenu))
            pulsante_assente.setTextSize(TypedValue.COMPLEX_UNIT_SP, size_scelto)
            attivita_fisica = "Assente"
        }

        val pulsante_leggera: TextView = dialogLayout.findViewById(R.id.textView_leggera)
        pulsante_leggera.setOnClickListener {
            pulisci_scritte_attivita_fisica(dialogLayout)
            pulsante_leggera.setTextColor(getColor(R.color.VerdeBordoSaluteMenu))
            pulsante_leggera.setTextSize(TypedValue.COMPLEX_UNIT_SP, size_scelto)
            attivita_fisica = "Leggera"
        }

        val pulsante_moderata: TextView = dialogLayout.findViewById(R.id.textView_moderata)
        pulsante_moderata.setOnClickListener {
            pulisci_scritte_attivita_fisica(dialogLayout)
            pulsante_moderata.setTextColor(getColor(R.color.VerdeBordoSaluteMenu))
            pulsante_moderata.setTextSize(TypedValue.COMPLEX_UNIT_SP, size_scelto)
            attivita_fisica = "Moderata"
        }

        val pulsante_pesante: TextView = dialogLayout.findViewById(R.id.textView_pesante)
        pulsante_pesante.setOnClickListener {
            pulisci_scritte_attivita_fisica(dialogLayout)
            pulsante_pesante.setTextColor(getColor(R.color.VerdeBordoSaluteMenu))
            pulsante_pesante.setTextSize(TypedValue.COMPLEX_UNIT_SP, size_scelto)
            attivita_fisica = "Pesante"
        }
    }

    fun pulisci_scritte_attivita_fisica(dialogLayout: View) {
        val size_default: Float = 16.0F

        val pulsante_assente: TextView = dialogLayout.findViewById(R.id.textView_assente)
        pulsante_assente.setTextColor(getColor(R.color.white))
        pulsante_assente.setTextSize(TypedValue.COMPLEX_UNIT_SP, size_default)

        val pulsante_leggera: TextView = dialogLayout.findViewById(R.id.textView_leggera)
        pulsante_leggera.setTextColor(getColor(R.color.white))
        pulsante_leggera.setTextSize(TypedValue.COMPLEX_UNIT_SP, size_default)

        val pulsante_moderata: TextView = dialogLayout.findViewById(R.id.textView_moderata)
        pulsante_moderata.setTextColor(getColor(R.color.white))
        pulsante_moderata.setTextSize(TypedValue.COMPLEX_UNIT_SP, size_default)

        val pulsante_pesante: TextView = dialogLayout.findViewById(R.id.textView_pesante)
        pulsante_pesante.setTextColor(getColor(R.color.white))
        pulsante_pesante.setTextSize(TypedValue.COMPLEX_UNIT_SP, size_default)
    }
}