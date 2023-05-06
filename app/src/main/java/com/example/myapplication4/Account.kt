package com.example.myapplication4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Account : AppCompatActivity() {
    private lateinit var myAdsBtn: Button
    private lateinit var myMembershipBtn: Button
    private lateinit var myProfileBtn: Button
    private lateinit var faqBtn: Button
    private lateinit var aboutUsBtn: Button
    private lateinit var logOutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        val uid = intent.getStringExtra("USER_UID")
        val email = intent.getStringExtra("email")

        myProfileBtn = findViewById(R.id.myProfileBtn)
        logOutBtn = findViewById(R.id.logOutBtn)

        myProfileBtn.setOnClickListener {
            val intent = Intent(this, EmpProfile::class.java)
            intent.putExtra("USER_UID", uid)
            intent.putExtra("email", email)
            startActivity(intent)
        }

        logOutBtn.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, EmpLogin::class.java)
            startActivity(intent)
        }
    }
}