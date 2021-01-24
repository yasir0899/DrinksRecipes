package com.example.drinksrecipes.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationHelper = NotificationHelper(context)
        val action: String = intent?.action ?: ""

        Log.i("Receiver", "Broadcast received: $action")

        if (action == "com.example.drinksrecipes") {
            val title = intent?.extras?.getString(AppConstants.NOTIFICATION_TITLE)
            val desc = intent?.extras?.getString(AppConstants.NOTIFICATION_DESC)
            val img = intent?.extras?.getString(AppConstants.NOTIFICATION_IMG)
            Log.e("Receiver", "Broadcast received: $title $desc $img ")
            notificationHelper.createNotification(title,desc,img)
        }
    }
}