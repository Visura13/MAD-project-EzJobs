package com.example.myapplication4

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EmpProfile : AppCompatActivity() {
    private lateinit var usernameText: EditText
    private lateinit var emailText: EditText
    private lateinit var phoneNumberText: EditText
    private lateinit var addressText: EditText
    private lateinit var worksAtText: EditText
    private lateinit var workPositionText: EditText
    private lateinit var editProfileBtn: Button
    private lateinit var deleteProfileBtn: Button
    private lateinit var backBtn: FloatingActionButton
    // Get a DatabaseReference object for the location where you want to write the data
    private val database = FirebaseDatabase.getInstance()
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_profile)

        val userRef = database.getReference("employers/$currentUserUid")
        usernameText = findViewById(R.id.profUsername)
        usernameText.isEnabled = false
        emailText = findViewById(R.id.profEmail)
        emailText.isEnabled = false
        phoneNumberText = findViewById(R.id.profPhoneNo)
        phoneNumberText.isEnabled = false
        addressText = findViewById(R.id.profAddress)
        addressText.isEnabled = false
        worksAtText = findViewById(R.id.profWorksAt)
        worksAtText.isEnabled = false
        workPositionText = findViewById(R.id.profWorkPosition)
        workPositionText.isEnabled = false
        editProfileBtn = findViewById(R.id.editProfBtn)
        deleteProfileBtn = findViewById(R.id.deleteProfBtn)
        backBtn = findViewById(R.id.backBtn)

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

        editProfileBtn.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java)
            startActivity(intent)
        }

        deleteProfileBtn.setOnClickListener{
            if (currentUserUid != null) {
                userRef.removeValue()
                    .addOnSuccessListener {
                        Log.d(TAG, "User data deleted from database.")
                    }
                    .addOnFailureListener { exception ->
                        Log.e(TAG, "Error deleting user data from database.", exception)
                    }
                user?.delete()
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "User profile deleted successfully", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "User profile deleted.")
                            FirebaseAuth.getInstance().signOut()
                            val intent = Intent(this, EmpLogin::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, "User profile deleting failed", Toast.LENGTH_SHORT).show()
                            Log.e(TAG, "Error deleting user profile.", task.exception)
                        }
                    }

            } else {
                Log.e(TAG, "User not logged in.")
            }
        }

        backBtn.setOnClickListener {
            val intent = Intent(this, Account::class.java)
            startActivity(intent)
        }
    }

}