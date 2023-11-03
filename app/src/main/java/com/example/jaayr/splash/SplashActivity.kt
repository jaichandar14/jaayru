package com.example.jaayr.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.jaayr.R
import com.example.jaayr.dashboard.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        findViewById<Button>(R.id.appCompatButton).setOnClickListener {
            startActivity( Intent(this, MainActivity::class.java))
        }
    }
}