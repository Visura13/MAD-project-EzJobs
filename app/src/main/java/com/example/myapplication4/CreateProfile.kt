package com.example.myapplication4

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.google.firebase.database.FirebaseDatabase

class CreateProfile : AppCompatActivity() {
    private lateinit var usernameText: EditText
    private lateinit var emailText: EditText
    private lateinit var phoneNumberText: EditText
    private lateinit var addressText: EditText
    private lateinit var worksAtText: EditText
    private lateinit var workPositionText: EditText
    private lateinit var saveProfileBtn: Button
    // Get a DatabaseReference object for the location where you want to write the data
    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        val uid = intent.getStringExtra("USER_UID")
        val email = intent.getStringExtra("email")
        usernameText = findViewById(R.id.cUsername)
        emailText = findViewById(R.id.cEmail)
        phoneNumberText = findViewById(R.id.cPhoneNo)
        addressText = findViewById(R.id.cAddress)
        worksAtText = findViewById(R.id.cWorksAt)
        workPositionText = findViewById(R.id.cWorkPost)
        saveProfileBtn = findViewById(R.id.createProfBtn)

        emailText.setText(email)

        saveProfileBtn.setOnClickListener {
            val username = usernameText.text.toString()
//            val email = emailText.text.toString()
            val phoneNumber = phoneNumberText.text.toString()
            val address = addressText.text.toString()
            val worksAt = worksAtText.text.toString()
            val workPosition = workPositionText.text.toString()

            val reference = database.getReference("employers")

            // Define the data to be written
            val data = hashMapOf(
                "username" to username,
                "email" to email,
                "phoneNumber" to phoneNumber,
                "address" to address,
                "worksAt" to worksAt,
                "workPosition" to workPosition,
            )

            // Write the data to the database
            if (uid != null) {
                reference.child(uid).setValue(data)
                    .addOnSuccessListener {
                        // Data was written successfully
                        Log.d(TAG, "Data was written successfully")
                        Toast.makeText(this, "Profile created successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, EmpLogin::class.java).apply {
                            putExtra("USER_UID", uid)
                        }
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        // Data writing failed
                        Toast.makeText(this, "Profile creation failed: ${it.message}", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Error writing data: ${it.message}")
                    }
            }
        }
    }



}