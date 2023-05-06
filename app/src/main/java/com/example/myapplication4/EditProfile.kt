package com.example.myapplication4

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditProfile : AppCompatActivity() {
    private lateinit var usernameText: EditText
    private lateinit var emailText: EditText
    private lateinit var phoneNumberText: EditText
    private lateinit var addressText: EditText
    private lateinit var worksAtText: EditText
    private lateinit var workPositionText: EditText
    private lateinit var passwordText: EditText
    private lateinit var changePassword: TextView
    private lateinit var saveChangesBtn: Button

    // Get a DatabaseReference object for the location where you want to write the data
    private val database = FirebaseDatabase.getInstance()
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val userRef = database.getReference("employers/$currentUserUid")
        usernameText = findViewById(R.id.editProfUsername)
        emailText = findViewById(R.id.editProfEmail)
        emailText.isEnabled = false
        phoneNumberText = findViewById(R.id.editProfPhoneNumber)
        addressText = findViewById(R.id.editProfAddress)
        worksAtText = findViewById(R.id.editProfWorksAt)
        workPositionText = findViewById(R.id.editProfWorkPosition)
        changePassword = findViewById(R.id.pwChange)
        saveChangesBtn = findViewById(R.id.saveChangesBtn)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the user's details from the dataSnapshot
                val username = dataSnapshot.child("username").getValue(String::class.java)
                val email = dataSnapshot.child("email").getValue(String::class.java)
                val phoneNumber = dataSnapshot.child("phoneNumber").getValue(String::class.java)
                val worksAt = dataSnapshot.child("worksAt").getValue(String::class.java)
                val workPosition = dataSnapshot.child("workPosition").getValue(String::class.java)
                val address = dataSnapshot.child("address").getValue(String::class.java)
                Log.d("Uid", currentUserUid.toString())
                Log.d("Uid", username.toString())
                // Set the values of the text fields
                usernameText.setText(username.toString())
                emailText.setText(email.toString())
                phoneNumberText.setText(phoneNumber.toString())
                worksAtText.setText(worksAt.toString())
                workPositionText.setText(workPosition.toString())
                addressText.setText(address.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Error", "Fetch Failed")
            }
        })

        changePassword.setOnClickListener{
            val intent = Intent(this, Password::class.java)
            startActivity(intent)
        }

        saveChangesBtn.setOnClickListener {
            val username = usernameText.text.toString()
            val email = emailText.text.toString()
            val phoneNumber = phoneNumberText.text.toString()
            val address = addressText.text.toString()
            val worksAt = worksAtText.text.toString()
            val workPosition = workPositionText.text.toString()

            val updatedData = hashMapOf(
                "username" to username,
                "email" to email,
                "phoneNumber" to phoneNumber,
                "address" to address,
                "worksAt" to worksAt,
                "workPosition" to workPosition,
            )
            if (currentUserUid != null) {
                userRef.setValue(updatedData)
                    .addOnSuccessListener {
                        // Data was udpated successfully
                        Log.d(TAG, "Data was updated successfully")
                        Toast.makeText(this, "Profile details updated successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, EmpProfile::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        // Data update failed
                        Toast.makeText(this, "Profile details update failed: ${it.message}", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Error updating data: ${it.message}")
                    }
            }

        }
    }

}