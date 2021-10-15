package com.example.iweather.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var activityMainBinding: ActivityMainBinding? = null
    private val viewBinding get() = activityMainBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }


    companion object{
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}