package com.example.ezjobs_job.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ezjobs_job.Models.JobModel
import com.example.ezjobs_job.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class JobDetailsActivity : AppCompatActivity() {
    private lateinit var tViewTitle: TextView
    private lateinit var tViewDescription: TextView
    private lateinit var tViewDeadline: TextView
    private lateinit var btnUpdateJob: Button
    private lateinit var btnDeleteJob: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_details)

        //initializing xml views
        initView()
        //get all job details from database
        setJobDetails()

        btnUpdateJob.setOnClickListener {
            //update job details and save to database
            openUpdateDialog(
                intent.getStringExtra("jobId").toString()
            )
        }
        btnDeleteJob.setOnClickListener {
            //delete job from database
            deleteRecord(
                intent.getStringExtra("jobId").toString()
            )
        }
    }

    private fun initView() {
        tViewTitle = findViewById(R.id.tViewTitle)
        tViewDescription = findViewById(R.id.tViewDescription)
        tViewDeadline = findViewById(R.id.tViewDeadline)

        btnUpdateJob = findViewById(R.id.btnUpdateJob)
        btnDeleteJob = findViewById(R.id.btnDeleteJob)
    }

    private fun setJobDetails(){
        tViewTitle.text = intent.getStringExtra("title")
        tViewDescription.text = intent.getStringExtra("description")
        tViewDeadline.text = intent.getStringExtra("deadline")
    }

    private fun openUpdateDialog(
        jobId: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val eTextTitle = mDialogView.findViewById<EditText>(R.id.eTextTitle)
        val eTextDescription = mDialogView.findViewById<EditText>(R.id.eTextDescription)
        val eTextDeadline = mDialogView.findViewById<EditText>(R.id.eTextDeadline)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        eTextTitle.setText(intent.getStringExtra("title").toString())
        eTextDescription.setText(intent.getStringExtra("description").toString())
        eTextDeadline.setText(intent.getStringExtra("deadline").toString())

        mDialog.setTitle("Update post")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                jobId,
                eTextTitle.text.toString(),
                eTextDescription.text.toString(),
                eTextDeadline.text.toString()
            )

            Toast.makeText(applicationContext, "Job Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textViews
            tViewTitle.text = eTextTitle.text.toString()
            tViewDescription.text = eTextDescription.text.toString()
            tViewDeadline.text = eTextDeadline.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        title: String,
        description: String,
        deadline: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Jobs").child(id)
        val jobInfo = JobModel(id, title, description, deadline)
        dbRef.setValue(jobInfo)
    }

    private fun deleteRecord(id: String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Jobs").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Job post deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, ViewJobActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

}