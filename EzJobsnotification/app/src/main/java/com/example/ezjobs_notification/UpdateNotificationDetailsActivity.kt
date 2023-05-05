package com.example.ezjobs_notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ezjobs_notification.databinding.ActivityUpdateNotificationDetailsBinding
import com.example.ezjobs_notification.databinding.ActivityViewNotificationDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateNotificationDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateNotificationDetailsBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNotificationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpdateDetails.setOnClickListener {
            val updatePhn = binding.updatePhn.text.toString()
            val updateCategory = binding.updateCategory.text.toString()
            val updateLocation = binding.updateLocation.text.toString()

            updateNotificationDetails(updatePhn, updateCategory, updateLocation)
        }
    }

    private fun updateNotificationDetails(phone: String, category: String, location: String) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Notifications")
        val notificationDetail = mapOf<String, String>("phone" to phone, "category" to category, "location" to location)

        databaseReference.child(phone).updateChildren(notificationDetail).addOnSuccessListener {

            binding.updatePhn.text.clear()
            binding.updateCategory.text.clear()
            binding.updateLocation.text.clear()

            Toast.makeText(
                this,
                "Updated",
                Toast.LENGTH_SHORT
            ).show()

        }.addOnFailureListener {
            Toast.makeText(
                this,
                "Unable to update",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}