package com.example.ezjobs_notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.ezjobs_notification.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubscribe.setOnClickListener{
            val intent = Intent(this@MainActivity, AddNotificationDetailsActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnView.setOnClickListener{
            val intent = Intent(this@MainActivity, ViewNotificationDetailsActivity::class.java)
            startActivity(intent)
        }
    }


}