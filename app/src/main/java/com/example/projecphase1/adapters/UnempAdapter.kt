package com.example.projecphase1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projecphase1.R
import com.example.projecphase1.models.UnemployeeModel

class UnempAdapter (private val unempList:ArrayList<UnemployeeModel>) :
    RecyclerView.Adapter<UnempAdapter.ViewHolder>(){

    private lateinit var mListener:onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.unemp_list_items,parent,false)
        return ViewHolder(itemView,mListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUnemp = unempList[position]
        holder.tvUnempName.text = currentUnemp.uname
    }



    override fun getItemCount(): Int {
        return unempList.size
    }

    class ViewHolder(itemView:View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvUnempName : TextView = itemView.findViewById(R.id.tvUnempName)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }

    }



}