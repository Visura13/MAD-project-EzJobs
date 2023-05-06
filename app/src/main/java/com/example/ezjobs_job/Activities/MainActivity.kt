package com.example.ezjobs_job.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ezjobs_job.R

class MainActivity : AppCompatActivity() {
    private lateinit var btnPostJob: Button
    private lateinit var btnViewJobs: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPostJob = findViewById(R.id.btnPostJob)
        btnViewJobs = findViewById(R.id.btnViewJobs)

        //navigate user to post job page
        btnPostJob.setOnClickListener {
            val intent = Intent(this, PostJobActivity::class.java)
            startActivity(intent)
        }

        //navigate user to all posted jobs page
        btnViewJobs.setOnClickListener {
            val intent = Intent(this, ViewJobActivity::class.java)
            startActivity(intent)
        }
    }
}