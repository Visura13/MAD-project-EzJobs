package com.example.myapplication4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class EmpLogin : AppCompatActivity() {
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    private lateinit var signInBtn: Button
    private lateinit var signUpBtn: TextView
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_login)

        emailText = findViewById(R.id.loginEmail)
        passwordText = findViewById(R.id.loginPassword)
        signInBtn = findViewById(R.id.loginBtn)
        signUpBtn = findViewById(R.id.signUpBtn)

        signInBtn.setOnClickListener {
            val email = emailText.text.toString()
            val password = passwordText.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)

            }
        }

        signUpBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login successful, go to the home screen or do any other action you need
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    val uid = auth.currentUser?.uid
                    val user = auth.currentUser
                    // Navigate to the next screen and pass the user's UID as an extra
                    val intent = Intent(this, Account::class.java)
                    intent.putExtra("USER_UID", uid)
                    intent.putExtra("email", email)
                    startActivity(intent)
                } else {
                    // Login failed, show an error message
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}