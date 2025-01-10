package com.funsol.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.squareup.picasso.Picasso
import java.util.concurrent.atomic.AtomicInteger

class FcmNotificationHandler(private val context: Context) {

    private val TAG = "FcmNotificationHandler"


    fun sendNotification(
        icon: String,
        title: String,
        shortDesc: String,
        image: String?,
        longDesc: String?,
        storePackage: String
    ) {
        val intent = if (!isAppInstalled(storePackage)) {
            createPlayStoreIntent(storePackage)
        } else {
            createOpenAppIntent(storePackage)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        )

        val remoteViews = RemoteViews(context.packageName, R.layout.firebase_notification_view)
        remoteViews.setTextViewText(R.id.tv_title, title)
        remoteViews.setTextViewText(R.id.tv_short_desc, shortDesc)

        val channelId = context.getString(R.string.default_notification_channel_id)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentIntent(pendingIntent)
            .setCustomContentView(remoteViews)
            .setCustomBigContentView(remoteViews)
            .setAutoCancel(true)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationID = AtomicInteger().incrementAndGet()
        notificationManager.notify(notificationID, notificationBuilder.build())

        loadImagesIntoViews(icon, image, remoteViews, notificationID, notificationBuilder)
    }

    private fun isAppInstalled(packageName: String): Boolean {
        return try {
            context.packageManager.getApplicationInfo(packageName, 0).enabled
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun createOpenAppIntent(packageName: String): Intent {
        return context.packageManager.getLaunchIntentForPackage(packageName)
            ?: createPlayStoreIntent(packageName)
    }

    private fun createPlayStoreIntent(packageName: String): Intent {
        return try {
            Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        } catch (e: ActivityNotFoundException) {
            Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
        }
    }

    private fun loadImagesIntoViews(
        icon: String,
        image: String?,
        remoteViews: RemoteViews,
        notificationID: Int,
        notificationBuilder: NotificationCompat.Builder
    ) {
        try {
            Picasso.get().load(icon).into(remoteViews, R.id.iv_icon, notificationID, notificationBuilder.build())
            if (!image.isNullOrEmpty()) {
                remoteViews.setViewVisibility(R.id.iv_feature, View.VISIBLE)
                Picasso.get().load(image).into(remoteViews, R.id.iv_feature, notificationID, notificationBuilder.build())
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading images into notification views: ${e.message}")
        }
    }

}