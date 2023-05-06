package com.example.projecphase1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecphase1.R
import com.example.projecphase1.adapters.UnempAdapter
import com.example.projecphase1.models.UnemployeeModel
import com.google.firebase.database.*

class editdetails : AppCompatActivity() {

    private lateinit var unempRecyclerView : RecyclerView
    private lateinit var tvLoadingData:TextView
    private lateinit var unempList : ArrayList<UnemployeeModel>
    private lateinit var dbRef : DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editdetails)

        unempRecyclerView= findViewById(R.id.rvUnemployee)
        unempRecyclerView.layoutManager = LinearLayoutManager(this)
        unempRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        unempList = arrayListOf<UnemployeeModel>()

        getUnemployeesData()


    }

    private fun getUnemployeesData(){
        unempRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Unemployee")

        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                unempList.clear()
                if(snapshot.exists()){
                    for(unempsnap in snapshot.children){
                        val UnempData = unempsnap.getValue(UnemployeeModel::class.java)
                        unempList.add(UnempData!!)

                    }
                    val mAdapter = UnempAdapter(unempList)
                    unempRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : UnempAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent  = Intent(this@editdetails, UnemployeeDetails::class.java)

                            //put extras
                            intent.putExtra("UnempID", unempList[position].UempID)
                            intent.putExtra("unempname", unempList[position].uname)
                            intent.putExtra("unempnic", unempList[position].unic)
                            intent.putExtra("unempnumber", unempList[position].unumber)
                            intent.putExtra("unempaddress", unempList[position].uaddress)
                            startActivity(intent)


                        }

                    })

                    unempRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



    }

}