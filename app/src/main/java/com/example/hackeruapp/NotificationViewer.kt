package com.example.hackeruapp

import android.app.*
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hackeruapp.model.Person

object NotificationViewer {

    val CHANNEL_ID = "Notification"

    fun createNotification(context: Context) {
        val name = "Notification Channel"
        val descriptionText = "Notification Chanel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("Notification", name, importance)
        channel.description = descriptionText

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun displayNotification(context: Context, person: Person) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        builder.setContentTitle("Notification")
        builder.setContentText("you have add ${person.name}")
        builder.setSmallIcon(com.google.android.material.R.drawable.notification_bg)
        builder.setAutoCancel(true)

        val nManagerCompat = NotificationManagerCompat.from(context)
        nManagerCompat.notify(1, builder.build())
    }

    fun notificationApplicationLaunch(context: Context): Notification {
        createNotification(context)
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("My service notification")
            .setSmallIcon(R.drawable.ic_baseline_home_24)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentText("Notification working in the background").build()
    }
}