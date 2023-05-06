package com.example.projecphase1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class changePWCode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pwcode)

        var button5 = findViewById<Button>(R.id.button5)
        button5.setOnClickListener{
            var intent = Intent(this,changepwenternew::class.java)
            startActivity(intent)
        }
    }
}