package com.example.ezjobs_notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ezjobs_notification.databinding.ActivityMainBinding
import com.example.ezjobs_notification.databinding.ActivityViewNotificationDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ViewNotificationDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewNotificationDetailsBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewNotificationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            val searchPhn: String = binding.searchPhn.text.toString()
            if (searchPhn.isNotEmpty()){
                readNotificationDetails(searchPhn)
            }
        }

        binding.btnUpdate.setOnClickListener{
            val intent = Intent(this@ViewNotificationDetailsActivity, UpdateNotificationDetailsActivity::class.java)
            startActivity(intent)
        }

        binding.btnDelete.setOnClickListener{
            val intent = Intent(this@ViewNotificationDetailsActivity, DltNotificationDetailsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun readNotificationDetails(phone: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Notifications")

        databaseReference.child(phone).get().addOnSuccessListener {
            if (it.exists()) {
                val phone = it.child("phone").value
                val category = it.child("category").value
                val location = it.child("location").value

                Toast.makeText(
                    this,
                    "Notification details fetched successfully",
                    Toast.LENGTH_SHORT
                ).show()

                binding.searchPhn.text.clear()
                binding.readPhn.text = phone.toString()
                binding.readCategory.text = category.toString()
                binding.readLocation.text = location.toString()

            } else {
                Toast.makeText(
                    this,
                    "Phone number is invalid",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.addOnFailureListener {
            Toast.makeText(
                this,
                "Phone number is invalid",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}