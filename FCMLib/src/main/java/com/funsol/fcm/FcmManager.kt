package com.funsol.fcm

import android.content.Context

interface FcmManager {

    fun setup(context: Context, topic: String)

    fun removeSubscription(topic: String)

}