package com.example.myapplication4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    private lateinit var registerBtn: Button
    private lateinit var signInBtn: TextView
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        emailText = findViewById(R.id.regEmpEmail)
        passwordText = findViewById(R.id.regEmpPassword)
        registerBtn = findViewById(R.id.regBtn)
        signInBtn = findViewById(R.id.signIn)

        registerBtn.setOnClickListener {
            val email = emailText.text.toString()
            val password = passwordText.text.toString()
           if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
               registerUser(email, password)

            }
        }

        signInBtn.setOnClickListener {
            val intent = Intent(this, EmpLogin::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    // Navigate to the next screen and pass the user's UID as an extra
                    val intent = Intent(this, CreateProfile::class.java)
                    intent.putExtra("USER_UID", uid)
                    intent.putExtra("email", email)
                    startActivity(intent)
                    Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                    Log.d("regSuccess", "Registration success:")
                    finish()

                } else {
                    Log.d("regFailed", "Registration failed: ${task.exception?.message}")
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}