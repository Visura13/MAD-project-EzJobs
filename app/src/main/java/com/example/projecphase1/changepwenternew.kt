package com.example.projecphase1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class changepwenternew : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepwenternew)

        var button6 = findViewById<Button>(R.id.button6)
        button6.setOnClickListener{
            var intent = Intent(this,passwordchanged::class.java)
            startActivity(intent)
        }
    }
}