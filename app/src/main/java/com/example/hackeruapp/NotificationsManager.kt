package com.example.hackeruapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationsManager {

    val CHANNEL_ID = "CHANNEL_ID"

    fun createNotificationChannel(context: Context) {
        val name = "Notification Channel"
        val descriptionText = "Notification Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = descriptionText

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun diaplay(context: Context, note: Note) {
        createNotificationChannel(context)
        val builer =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("MyNotification")
                .setSmallIcon(R.drawable.camera)
                .setContentText("Hey! Note -${note.title} has been added to your list")
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(1, builer.build())
    }
}