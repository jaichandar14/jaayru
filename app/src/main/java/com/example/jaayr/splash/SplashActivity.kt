package com.example.jaayr.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.jaayr.R
import com.example.jaayr.dashboard.MainActivity
import com.example.jaayr.registeration.RegActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        findViewById<Button>(R.id.appCompatButton).setOnClickListener {
            startActivity(Intent(this, RegActivity::class.java))
        }
        val sh = getSharedPreferences("myAppPreferences", MODE_PRIVATE)
        val s1 = sh.getString("uid", "")
        Log.d("TAG", "onCreate: $s1")
        if (s1.isNullOrBlank()) {
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}