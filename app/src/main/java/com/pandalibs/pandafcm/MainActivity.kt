package com.pandalibs.pandafcm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pandalibs.fcmlib.PandaFCM

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PandaFCM.setupFCM(applicationContext, packageName)
    }
}