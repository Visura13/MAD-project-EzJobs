package com.example.projecphase1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class changepw : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepw)

        var button4 = findViewById<Button>(R.id.button4)
        button4.setOnClickListener{
            var intent = Intent(this,changePWCode::class.java)
            startActivity(intent)
        }


    }
}