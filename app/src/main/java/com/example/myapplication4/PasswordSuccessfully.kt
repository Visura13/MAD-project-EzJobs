package com.example.myapplication4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PasswordSuccessfully : AppCompatActivity() {
    private lateinit var backToProfile: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_successfully)

        backToProfile = findViewById(R.id.backToProf)
        backToProfile.setOnClickListener {
            val intent = Intent(this, EmpProfile::class.java)
            startActivity(intent)
        }
    }

}