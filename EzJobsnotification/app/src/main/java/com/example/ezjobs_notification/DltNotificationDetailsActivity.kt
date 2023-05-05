package com.example.ezjobs_notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ezjobs_notification.databinding.ActivityDltNotificationDetailsBinding
import com.example.ezjobs_notification.databinding.ActivityViewNotificationDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DltNotificationDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDltNotificationDetailsBinding
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDltNotificationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirmDlt.setOnClickListener {
            val phone: String = binding.searchPhnDlt.text.toString()
            deleteNotificationDetails(phone)
        }
    }

    private fun deleteNotificationDetails(phone: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Notifications")
        databaseReference.child(phone).removeValue().addOnSuccessListener{

            Toast.makeText(
                this,
                "Delete successfully",
                Toast.LENGTH_SHORT
            ).show()

        }.addOnFailureListener {
            Toast.makeText(
                this,
                "Unable to delete",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}