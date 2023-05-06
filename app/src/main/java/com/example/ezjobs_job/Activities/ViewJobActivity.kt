package com.example.ezjobs_job.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ezjobs_job.Adapters.JobAdapter
import com.example.ezjobs_job.Models.JobModel
import com.example.ezjobs_job.R
import com.google.firebase.database.*

class ViewJobActivity : AppCompatActivity() {
    private lateinit var rViewJobAds: RecyclerView
    private lateinit var tViewLoading: TextView
    private lateinit var jobList: ArrayList<JobModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_job)

        rViewJobAds = findViewById(R.id.rViewJobAds)
        rViewJobAds.layoutManager = LinearLayoutManager(this)
        rViewJobAds.setHasFixedSize(true)
        tViewLoading = findViewById(R.id.tViewLoading)

        //create arraylist of all posted jobs
        jobList = arrayListOf()
        //getting all jobs from database
        getJobData()
    }

    private fun getJobData(){
        rViewJobAds.visibility = View.GONE
        tViewLoading.visibility = View.VISIBLE

        //create database reference
        dbRef = FirebaseDatabase.getInstance().getReference("Jobs")

        //fetching all jobs from database
        dbRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                jobList.clear()
                if (snapshot.exists()){
                    for (jobSnap in snapshot.children){
                        val jobData = jobSnap.getValue(JobModel::class.java)
                        jobList.add(jobData!!)
                    }
                    val jAdapter = JobAdapter(jobList)
                    rViewJobAds.adapter = jAdapter

                    jAdapter.setOnJobClickListener(object : JobAdapter.onJobClickListener{
                        override fun onJobClick(position: Int) {
                            val intent = Intent(this@ViewJobActivity, JobDetailsActivity::class.java)

                            //put extra details
                            intent.putExtra("jobId", jobList[position].jobId)
                            intent.putExtra("title", jobList[position].title)
                            intent.putExtra("description", jobList[position].description)
                            intent.putExtra("deadline", jobList[position].deadline)
                            startActivity(intent)
                        }

                    })

                    rViewJobAds.visibility = View.VISIBLE
                    tViewLoading.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}