package com.example.salutemendietamediterranea

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.content.Intent
import android.provider.Settings
import android.net.Uri
import androidx.core.app.NotificationManagerCompat

class Gestione_notifiche : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gestione_notifiche)

        val bottoneConsentiNotifiche: Button = findViewById(R.id.bottone_consenti_notifiche)
        val pulsanteIndietro = findViewById<ImageButton>(R.id.indietro)
        val bottoneMessaggio: Button = findViewById(R.id.bottone_ricevi_messaggio)

        if (!isNotificationPermissionGranted(this)) {
            bottoneMessaggio.visibility = View.GONE
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        }

        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                if (isNotificationPermissionGranted(this@Gestione_notifiche)) {
                    bottoneConsentiNotifiche.visibility = View.GONE
                    bottoneMessaggio.visibility = View.VISIBLE
                }
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)

        bottoneConsentiNotifiche.setOnClickListener {
            val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            var permessoConteggio = sharedPreferences.getBoolean("NotificationPermissionCounter2", false)

            if (!isNotificationPermissionGranted(this@Gestione_notifiche) && !permessoConteggio){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            } else if (!isNotificationPermissionGranted(this@Gestione_notifiche) && permessoConteggio){
                AlertDialog.Builder(this)
                    .setTitle("Autorizzazione necessaria")
                    .setMessage("SaluteMenù necessita la tua autorizzazione per inviarti notifiche.")
                    .setPositiveButton("OK") { _, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.parse("package:${packageName}")
                        }
                        startActivity(intent)
                    }
                    .setNegativeButton("Annulla", null)
                    .show()
            }
        }

        pulsanteIndietro.setOnClickListener {
            finish()
        }

        bottoneMessaggio.setOnClickListener{
            val intent = Intent(this, NotificationReceiver::class.java)
            sendBroadcast(intent)
        }
    }

    private fun isNotificationPermissionGranted(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.areNotificationsEnabled()
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {

            val sharedPreferencesControllo = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            var VariabileControllo = sharedPreferencesControllo.getBoolean("NotificationPermissionCounter1", false)

            val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            if(VariabileControllo){
                editor.putBoolean("NotificationPermissionCounter2", true)
            }
            editor.putBoolean("NotificationPermissionCounter1", true)
            editor.apply()
        }
    }
}
