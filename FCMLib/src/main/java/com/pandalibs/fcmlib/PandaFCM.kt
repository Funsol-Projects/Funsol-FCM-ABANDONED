package com.pandalibs.fcmlib

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.pandalibs.fcmlib.PandaFirebaseMessagingService.Companion.TAG
import kotlinx.coroutines.runBlocking

class PandaFCM {

    companion object {
        fun setupFCM(context: Context, topic: String) {
            runBlocking {
                initializeFirebase(context)
                createChannelForFCM(context)
                FirebaseMessaging.getInstance().subscribeToTopic(topic)
                Log.i(TAG, "setupFCM: Panda Setup Successful")
            }
        }

        fun removeFCM(topic: String) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
        }

        private fun initializeFirebase(context: Context) {
            try {
                FirebaseApp.initializeApp(context)
                Log.i(TAG, "setupFCM: Firebase Initialized")
            } catch (e: Exception) {
                Log.i(TAG, "setupFCM: ${e.message}")
            }
        }

        private fun createChannelForFCM(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = context.getString(R.string.default_notification_channel_id)
                val channelName = context.getString(R.string.default_notification_channel_id)
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(
                    NotificationChannel(
                        channelId,
                        channelName, NotificationManager.IMPORTANCE_DEFAULT
                    )
                )
                Log.i(TAG, "setupFCM: Channel Created Successfully")
            }
        }
    }
}