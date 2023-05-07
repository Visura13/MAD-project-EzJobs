package com.example.ezjobs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        val member = findViewById<Button>(R.id.btnMember)
        val verifiedMember = findViewById<Button>(R.id.btnVerifiedMember)
        val authAgent = findViewById<Button>(R.id.btnAuthAgent)

        //navigate to the member page when clicked member btn
        member.setOnClickListener {
            val intent = Intent(this, MemberActivity::class.java)
            startActivity(intent)
        }

        //navigate to the verified member page when clicked verified member btn
        verifiedMember.setOnClickListener {
            val intent = Intent(this, VerifiedMemberActivity::class.java)
            startActivity(intent)
        }

        //navigate to the auth agent page when clicked auth agent btn
        authAgent.setOnClickListener {
            val intent = Intent(this, AuthAgentActivity::class.java)
            startActivity(intent)
        }
    }
}