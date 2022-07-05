package com.pandalibs.pandafcm

import android.content.ActivityNotFoundException
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import com.google.firebase.FirebaseApp
import com.pandalibs.fcmlib.PandaFCM
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        PandaFCM.setupFCM(this, "packagee")
    }
}