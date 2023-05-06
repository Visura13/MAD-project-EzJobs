package com.example.ezjobs_job.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ezjobs_job.Models.JobModel
import com.example.ezjobs_job.R

class JobAdapter (private val jobList: ArrayList<JobModel>):
    RecyclerView.Adapter<JobAdapter.ViewHolder>(){

    private lateinit var jListener : onJobClickListener

    interface onJobClickListener{
        fun onJobClick(position: Int)
    }

    fun setOnJobClickListener(clickListener: onJobClickListener){
        jListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_job, parent, false)
        return ViewHolder(itemView, jListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentJob = jobList[position]
        holder.jobTitle.text = currentJob.title
    }

    override fun getItemCount(): Int {
        return jobList.size
    }

    class ViewHolder(itemView: View, clickListener: onJobClickListener) : RecyclerView.ViewHolder(itemView){
        val jobTitle : TextView = itemView.findViewById(R.id.jobCard)

        init {
            itemView.setOnClickListener {
                clickListener.onJobClick(adapterPosition)
            }
        }

    }

}