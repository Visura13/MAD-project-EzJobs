package com.example.projecphase1.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.projecphase1.R


class profile : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        var button3 = findViewById<Button>(R.id.button3)
        var button7 = findViewById<Button>(R.id.button7)

        button7.setOnClickListener{
            var intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        button3.setOnClickListener{
            var intent = Intent(this, editdetails::class.java)
            startActivity(intent)
        }
    }
}