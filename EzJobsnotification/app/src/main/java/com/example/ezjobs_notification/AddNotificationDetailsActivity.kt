package com.example.ezjobs_notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ezjobs_notification.databinding.ActivityAddNotificationDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNotificationDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotificationDetailsBinding
    private lateinit var databaseReference: DatabaseReference

    //val jobCategories = arrayOf("Agriculture","Airline","Engineering","Health Care","Finance")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotificationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPay.setOnClickListener{
            val phone = binding.phnnumber.text.toString()
            val category = binding.jobCategory.text.toString()
            val location = binding.jobLocation.text.toString()

            databaseReference = FirebaseDatabase.getInstance().getReference("Notifications")
            val notification = NotificationModel(phone, category, location)
            databaseReference.child(phone).setValue(notification).addOnSuccessListener {
                binding.phnnumber.text.clear()
                binding.jobCategory.text.clear()
                binding.jobLocation.text.clear()

                Toast.makeText(this, "Notification details added successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@AddNotificationDetailsActivity, MainActivity::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener{
                Toast.makeText(this, "failed to add data", Toast.LENGTH_SHORT).show()
            }
        }


    }
}