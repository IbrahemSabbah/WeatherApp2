package com.example.iweather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iweather.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(MainActivity.getIntent(applicationContext))
    }
}