package com.example.ezjobs_job.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ezjobs_job.Models.JobModel
import com.example.ezjobs_job.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PostJobActivity : AppCompatActivity() {
    private lateinit var jobTitle: EditText
    private lateinit var jobDescription: EditText
    private lateinit var jobDeadline: EditText
    private lateinit var btnPost: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_job)

        jobTitle = findViewById(R.id.jobTitle)
        jobDescription = findViewById(R.id.jobDescription)
        jobDeadline = findViewById(R.id.jobDeadline)
        btnPost = findViewById(R.id.btnPost)

        dbRef = FirebaseDatabase.getInstance().getReference("Jobs")

        btnPost.setOnClickListener {
            saveJobData()
        }
    }

    private fun saveJobData(){
        //getting user input values
        val title = jobTitle.text.toString()
        val description = jobDescription.text.toString()
        val deadline = jobDeadline.text.toString()

        if (title.isEmpty()){
            jobTitle.error = "Job title is required"
            return
        }
        if (description.isEmpty()){
            jobDescription.error = "Job description is required"
            return
        }
        if (deadline.isEmpty()){
            jobDeadline.error = "Job deadline is required"
            return
        }

        //getting unique id
        val jobId = dbRef.push().key!!

        //create instance of job model
        val job = JobModel(jobId, title, description, deadline)
        //pass job data to database
        dbRef.child(jobId).setValue(job).addOnCompleteListener {
            Toast.makeText(this, "Job posted successfully", Toast.LENGTH_SHORT).show()
            jobTitle.text.clear()
            jobDescription.text.clear()
            jobDeadline.text.clear()

        }.addOnFailureListener {
            Toast.makeText(this, "Unable to post", Toast.LENGTH_SHORT).show()
        }
    }
}