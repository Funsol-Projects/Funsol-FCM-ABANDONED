package com.funsol.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.runBlocking

/**
 * Implementation of [FcmManager] to handle Firebase Cloud Messaging (FCM) setup and subscription management.
 *
 * This class is responsible for:
 * - Initializing Firebase.
 * - Managing FCM topic subscriptions.
 * - Creating notification channels (for Android Oreo and above).
 */
class FunsolFCM : FcmManager {

    private val TAG = "FunsolFCM"

    /**
     * Sets up Firebase Cloud Messaging for the app.
     *
     * This includes:
     * - Initializing Firebase.
     * - Creating a default notification channel.
     * - Subscribing to the specified FCM topic.
     *
     * @param context The [Context] required for Firebase and Notification Manager setup.
     * @param topic The topic to which the app will subscribe for receiving FCM messages.
     */
    override fun setup(context: Context, topic: String) {
        runBlocking {
            initializeFirebase(context)
            createNotificationChannel(context)
            FirebaseMessaging.getInstance().subscribeToTopic(topic)
        }
    }

    /**
     * Removes the app's subscription to a specified FCM topic.
     *
     * @param topic The topic from which the app will unsubscribe.
     */
    override fun removeSubscription(topic: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
    }

    /**
     * Initializes Firebase for the app.
     *
     * This method ensures that Firebase services are properly set up.
     * If initialization fails, an error is logged.
     *
     * @param context The [Context] required for Firebase initialization.
     */
    private fun initializeFirebase(context: Context) {
        try {
            FirebaseApp.initializeApp(context)
        } catch (e: Exception) {
            Log.e(TAG, "Firebase initialization failed: ${e.message}")
        }
    }

    /**
     * Creates a default notification channel for Firebase Cloud Messaging notifications.
     *
     * Required for Android Oreo (API level 26) and above. If the notification channel
     * already exists, this method does nothing.
     *
     * @param context The [Context] required to access the Notification Manager.
     */
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = context.getString(R.string.default_notification_channel_id)
            val channelName = context.getString(R.string.default_notification_channel_name)
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (notificationManager.getNotificationChannel(channelId) == null) {
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}