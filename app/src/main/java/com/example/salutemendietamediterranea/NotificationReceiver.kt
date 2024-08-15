package com.example.salutemendietamediterranea

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlin.random.Random

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val channelId = "MESSAGGIO MOTIVAZIONALE"
        val notificationId = 1

        createNotificationChannel(context, channelId)

        val fraseCasuale = getRandomFrase(context)

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText(fraseCasuale)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(fraseCasuale)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            val notificationManager = NotificationManagerCompat.from(context)
            if (notificationManager.areNotificationsEnabled()) {
                notify(notificationId, notificationBuilder.build())
                }
            }
    }

    private fun createNotificationChannel(context: Context, channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Messaggi motivazionali"
            val descriptionText = "Canale per notifiche messaggi motivazionali"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun getRandomFrase(context: Context): String {
        val stringIds = listOf(
            R.string.frase_1,
            R.string.frase_2,
            R.string.frase_3,
            R.string.frase_4,
            R.string.frase_5,
            R.string.frase_6,
            R.string.frase_7,
            R.string.frase_8,
            R.string.frase_9,
            R.string.frase_10,
            R.string.frase_11,
            R.string.frase_12,
            R.string.frase_13,
            R.string.frase_14,
            R.string.frase_15,
            R.string.frase_16,
            R.string.frase_17,
            R.string.frase_18,
            R.string.frase_19,
            R.string.frase_20,
            R.string.frase_21,
            R.string.frase_22,
            R.string.frase_23,
            R.string.frase_24,
            R.string.frase_25,
            R.string.frase_26,
            R.string.frase_27,
            R.string.frase_28,
            R.string.frase_29,
            R.string.frase_30
        )

        val randomId = stringIds[Random.nextInt(stringIds.size)]
        return context.getString(randomId)
    }
}