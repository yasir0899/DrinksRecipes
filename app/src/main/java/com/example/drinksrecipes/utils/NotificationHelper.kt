package com.example.drinksrecipes.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.example.drinksrecipes.R
import com.example.drinksrecipes.activities.MainActivity


internal class NotificationHelper(context: Context) {
    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "10001"
    }

    private val mContext: Context = context
    fun createNotification(title: String?, desc: String?, img: String?) {

        val intent = Intent(mContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val resultPendingIntent = PendingIntent.getActivity(
            mContext,
            0 /* Request code */, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val contentView =
            RemoteViews(AppController.ApplicationContext.packageName, R.layout.notification_layout)
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        if (img == "test") {
            contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        } else {
            contentView.setImageViewBitmap(
                R.id.image,

                InternalStorageProvider(mContext).loadBitmap(img.toString())
            )

        }
        contentView.setTextViewText(R.id.textView, "Alcohol")
        //contentView.setBoolean(R.id.cbAlcohol, "",false)
        if (title?.isNotEmpty() != null) {
            contentView.setTextViewText(R.id.tvTitle, title)
            contentView.setTextViewText(R.id.tvDesc, desc)
        } else {
            contentView.setTextViewText(R.id.tvTitle, "Need some drinks open app now")
            contentView.setTextViewText(R.id.tvDesc, "")
        }
        val mBuilder =
            NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(contentView)
                .build()
        val mNotificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "NOTIFICATION_CHANNEL_NAME",
                importance
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(
                100,
                200,
                300,
                400,
                500,
                400,
                300,
                200,
                400
            )
            assert(mNotificationManager != null)
            // mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
        assert(mNotificationManager != null)
        mNotificationManager.notify(0 /* Request Code */, mBuilder)
        Log.e("test", "$title")


    }
}