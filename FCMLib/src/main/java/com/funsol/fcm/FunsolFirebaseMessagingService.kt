package com.funsol.fcm

import android.os.Handler
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * A custom Firebase Messaging Service implementation for handling incoming Firebase Cloud Messaging (FCM) messages.
 *
 * This class processes FCM messages and handles new token generation. Notifications are managed
 * via the [FcmNotificationHandler] class.
 */
class FunsolFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "FunsolFirebaseMessagingService"

    /**
     * Called when a message is received from FCM.
     *
     * @param remoteMessage The [RemoteMessage] containing data or notification payload.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            processMessage(remoteMessage.data)
        }
    }

    /**
     * Called when a new FCM token is generated.
     *
     * @param token The new FCM registration token.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "New FCM Token: $token")
    }

    /**
     * Processes the incoming message data and delegates notification creation.
     *
     * @param data A map containing the message payload data.
     * Expected keys:
     * - `icon`: URL of the notification icon (required).
     * - `title`: Notification title (required).
     * - `short_desc`: Short description for the notification (required).
     * - `feature`: (Optional) URL of a feature image for the notification.
     * - `long_desc`: (Optional) Detailed description for the notification.
     * - `package`: (Optional) Target app package name.
     */
    private fun processMessage(data: Map<String, String>) {
        val icon = data["icon"] ?: return
        val title = data["title"] ?: return
        val shortDesc = data["short_desc"] ?: return
        val image = data["feature"]
        val longDesc = data["long_desc"]
        val packageName = data["package"]

        if (!packageName.isNullOrEmpty()) {
            Handler(mainLooper).post {
                FcmNotificationHandler(this).sendNotification(
                    icon, title, shortDesc, image, longDesc, packageName
                )
            }
        }
    }
}