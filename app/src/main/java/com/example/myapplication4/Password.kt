package com.example.myapplication4

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Password : AppCompatActivity() {
    private lateinit var passwordText: EditText
    private lateinit var confirmPasswordText: EditText
    private lateinit var savePasswordBtn: Button

    // Get a DatabaseReference object for the location where you want to write the data
    private val database = FirebaseDatabase.getInstance()
    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        val userRef = database.getReference("employers/$currentUserUid")
        passwordText = findViewById(R.id.editPasswordNew)
        confirmPasswordText = findViewById(R.id.editPasswordConf)
        savePasswordBtn = findViewById(R.id.savePwBtn)

        savePasswordBtn.setOnClickListener {
            val password = passwordText.text.toString()
            val confirmPassword = confirmPasswordText.text.toString()
                if(password != confirmPassword){
                    Toast.makeText(this, "Passwords are not matching", Toast.LENGTH_SHORT).show()
                }else{
                    user?.updatePassword(password)
                        ?.addOnCompleteListener { passwordTask ->
                            if (passwordTask.isSuccessful) {
                                Log.d(TAG, "Password updated successfully")
                                Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, PasswordSuccessfully::class.java)
                                startActivity(intent)
                            } else {
                                Log.e(TAG, "Error updating password", passwordTask.exception)
                                Toast.makeText(this, "Password update failed", Toast.LENGTH_SHORT).show()
                                Log.e(TAG, "Error updating data")
                            }
                        }
                    }
                }
    }

}